package com.vmedia.feature.feed.presentation.recycler.holder

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.vmedia.core.common.android.util.children
import com.vmedia.core.common.android.util.getColorCompat
import com.vmedia.core.common.pure.obj.event.EventInfo.EventListInfo
import com.vmedia.core.common.pure.util.onEachIndexed
import com.vmedia.feature.feed.presentation.R
import com.vmedia.feature.feed.presentation.recycler.FeedViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.feed_item.*

internal abstract class AssetListViewHolder<Content, Event : EventListInfo<Content>, ItemVH : ItemViewHolder<Content>>(
    containerView: View,
    onClick: (position: Int) -> Unit,
    onOptionsClick: (position: Int) -> Unit,
    private val onAssetClick: (position: Int, assetIndex: Int) -> Unit
) : FeedViewHolder<Event>(containerView, onClick, onOptionsClick) {

    private val assetViewHolders = feed_item_content.getChildAt(0).children
        .map(::onItemViewHolderCreate)
        .onEachIndexed { index, item ->
            item.containerView.setOnClickListener { safeOnClick(index) }
        }
        .toList()


    init {
        for (i in assetViewHolders.indices step 2) {
            val cardView = assetViewHolders[i].containerView as MaterialCardView
            cardView.setCardBackgroundColor(context.getColorCompat(R.color.brand_grey_lightest))
        }
    }


    @CallSuper
    override fun bindContent(item: Event) {
        val itemsCount = item.content.size

        assetViewHolders.forEachIndexed { index, viewHolder ->
            val itemExists = index < itemsCount

            viewHolder.containerView.isVisible = itemExists
            if (itemExists) viewHolder.bind(item.content[index])
        }
    }

    protected abstract fun onItemViewHolderCreate(view: View): ItemVH

    private fun safeOnClick(assetIndex: Int) {
        if (!hasPosition) return
        onAssetClick.invoke(adapterPosition, assetIndex)
    }

}

internal abstract class ItemViewHolder<Content>(
    override val containerView: View
) : LayoutContainer {

    abstract fun bind(content: Content)

    protected fun getString(@StringRes textResource: Int): String {
        return containerView.context.getString(textResource)
    }

}