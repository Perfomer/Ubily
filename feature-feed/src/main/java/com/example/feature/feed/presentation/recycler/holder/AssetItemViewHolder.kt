package com.example.feature.feed.presentation.recycler.holder

import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.example.feature.feed.presentation.recycler.FeedViewHolder
import com.vmedia.core.common.obj.descriptionResource
import com.vmedia.core.common.obj.labelResource
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.setOnClickListener
import com.vmedia.core.common.util.toSpan
import com.vmedia.core.data.obj.AssetInfo
import com.vmedia.core.data.obj.EventInfo.EventAsset
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.feed_item.*
import kotlinx.android.synthetic.main.feed_item_asset.*
import kotlinx.android.synthetic.main.feed_item_asset_item.*

internal class AssetViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit,
    private val onAssetClick: (position: Int, assetIndex: Int) -> Unit
) : FeedViewHolder<EventAsset>(containerView, onClick) {

    private val assetViewHolders = listOf(
        AssetItemViewHolder(feed_item_asset_1) { safeOnClick(0) },
        AssetItemViewHolder(feed_item_asset_2) { safeOnClick(1) },
        AssetItemViewHolder(feed_item_asset_3) { safeOnClick(2) }
    )

    init {
        assetViewHolders[1].containerView.background = null
    }

    override fun bindContent(item: EventAsset) {
        fun bindAsset(index: Int) {
            val asset = item.assets.getOrNull(index)
            val assetViewHolder = assetViewHolders[index]

            assetViewHolder.containerView.isVisible = asset != null
            asset?.let(assetViewHolder::bind)
        }

        repeat(assetViewHolders.size, ::bindAsset)

        feed_item_description.text = context.getString(
            item.type.descriptionResource,
            item.assets.size
        ).toSpan()
    }

    private fun safeOnClick(assetIndex: Int) {
        if (!hasPosition) return
        onAssetClick.invoke(adapterPosition, assetIndex)
    }

}

private class AssetItemViewHolder(
    override val containerView: View,
    onClick: () -> Unit
) : LayoutContainer {

    init {
        containerView.setOnClickListener(onClick)
    }

    fun bind(item: AssetInfo) {
        feed_item_asset_title.diffedValue = item.name
        feed_item_asset_version.diffedValue = item.version
        feed_item_asset_status.diffedValue = getString(item.status.labelResource)

        feed_item_asset_icon.load(item.icon) {
            crossfade(true)
            transformations(RoundedCornersTransformation(8.0f))
        }
    }

    private fun getString(@StringRes textResource: Int): String {
        return containerView.context.getString(textResource)
    }

}