package com.example.feature.feed.presentation.recycler.holder.asset

import android.view.View
import com.example.feature.feed.presentation.recycler.holder.AssetListViewHolder
import com.example.feature.feed.presentation.recycler.holder.ItemViewHolder
import com.vmedia.core.common.obj.descriptionResource
import com.vmedia.core.common.obj.labelResource
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.loadRoundedCorners
import com.vmedia.core.common.util.toSpan
import com.vmedia.core.data.obj.AssetInfo
import com.vmedia.core.data.obj.EventInfo.EventListInfo.EventAsset
import kotlinx.android.synthetic.main.feed_item.*
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

    override fun bindContent(item: EventAsset) {
        super.bindContent(item)

        feed_item_description.text = context.getString(
            item.type.descriptionResource,
            item.content.size
        ).toSpan()
    }

    override fun onItemViewHolderCreate(view: View) = AssetItemViewHolder(view)

}

internal class AssetItemViewHolder(
    containerView: View
) : ItemViewHolder<AssetInfo>(containerView) {

    override fun bind(content: AssetInfo) {
        feed_item_asset_title.diffedValue = content.name
        feed_item_asset_version.diffedValue = content.version
        feed_item_asset_status.diffedValue = getString(content.status.labelResource)
        feed_item_asset_icon.loadRoundedCorners(content.icon)
    }

}