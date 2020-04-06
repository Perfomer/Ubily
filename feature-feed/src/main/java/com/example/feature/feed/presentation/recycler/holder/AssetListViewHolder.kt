package com.example.feature.feed.presentation.recycler.holder

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.example.feature.feed.R
import com.example.feature.feed.presentation.recycler.FeedViewHolder
import com.vmedia.core.common.util.children
import com.vmedia.core.common.util.getDrawableCompat
import com.vmedia.core.common.util.onEachIndexed
import com.vmedia.core.data.obj.EventInfo.EventListInfo
import kotlinx.android.extensions.LayoutContainer

internal abstract class AssetListViewHolder<Content, Event : EventListInfo<Content>, ItemVH : ItemViewHolder<Content>>(
    containerView: View,
    onClick: (position: Int) -> Unit,
    private val onAssetClick: (position: Int, assetIndex: Int) -> Unit
) : FeedViewHolder<Event>(containerView, onClick) {

    private val assetViewHolders = containerView.children
        .map(::onItemViewHolderCreate)
        .onEachIndexed { index, item ->
            item.containerView.setOnClickListener { safeOnClick(index) }
        }
        .toList()


    init {
        for (i in assetViewHolders.indices step 2) {
            assetViewHolders[i].containerView.background = context.getDrawableCompat(
                R.drawable.bg_event_item
            )
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
): LayoutContainer {

    abstract fun bind(content: Content)

    protected fun getString(@StringRes textResource: Int): String {
        return containerView.context.getString(textResource)
    }

}