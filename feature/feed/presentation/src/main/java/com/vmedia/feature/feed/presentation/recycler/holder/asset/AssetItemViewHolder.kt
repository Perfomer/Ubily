package com.vmedia.feature.feed.presentation.recycler.holder.asset

import android.view.View
import com.vmedia.core.common.android.util.diffedValue
import com.vmedia.core.common.android.util.labelResource
import com.vmedia.core.common.android.util.loadRoundedImage
import com.vmedia.core.common.pure.obj.event.AssetInfo
import com.vmedia.core.common.pure.obj.event.EventInfo.EventListInfo.EventAsset
import com.vmedia.feature.feed.presentation.recycler.holder.AssetListViewHolder
import com.vmedia.feature.feed.presentation.recycler.holder.ItemViewHolder
import kotlinx.android.synthetic.main.feed_item_asset_item.*

internal class AssetViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit,
    onOptionsClick: (position: Int) -> Unit,
    onAssetClick: (position: Int, assetIndex: Int) -> Unit
) : AssetListViewHolder<AssetInfo, EventAsset, AssetItemViewHolder>(
    containerView,
    onClick,
    onOptionsClick,
    onAssetClick
) {

    override fun onItemViewHolderCreate(view: View) = AssetItemViewHolder(view)

}

internal class AssetItemViewHolder(
    containerView: View
) : ItemViewHolder<AssetInfo>(containerView) {

    override fun bind(content: AssetInfo) {
        feed_item_asset_title.diffedValue = content.name
        feed_item_asset_version.diffedValue = content.version
        feed_item_asset_status.diffedValue = getString(content.status.labelResource)
        feed_item_asset_icon.loadRoundedImage(content.icon)
    }

}