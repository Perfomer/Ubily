package com.vmedia.feature.eventdetails.presentation.viewholder.asset.item

import android.view.View
import com.vmedia.core.common.obj.event.AssetInfo
import com.vmedia.core.common.obj.labelResource
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.loadImageWithRoundedCorners
import com.vmedia.feature.eventdetails.presentation.viewholder.asset.recycler.BaseAssetItemViewHolder
import kotlinx.android.synthetic.main.eventdetails_item_asset_item.*

internal class AssetItemViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit
) : BaseAssetItemViewHolder<AssetInfo>(containerView, onClick) {

    override fun bind(item: AssetInfo) {
        super.bind(item)
        eventdetails_item_asset_title.diffedValue = item.name
        eventdetails_item_asset_version.diffedValue = item.version
        eventdetails_item_asset_status.diffedValue = getString(item.status.labelResource)
        eventdetails_item_asset_icon.loadImageWithRoundedCorners(item.icon)
    }

}