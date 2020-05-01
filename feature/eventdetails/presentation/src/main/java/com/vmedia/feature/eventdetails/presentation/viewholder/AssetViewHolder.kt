package com.vmedia.feature.eventdetails.presentation.viewholder

import android.content.Context
import android.view.View
import com.vmedia.core.common.pure.obj.EventType
import com.vmedia.core.common.pure.obj.event.AssetInfo
import com.vmedia.feature.eventdetails.presentation.R
import com.vmedia.feature.eventdetails.presentation.viewholder.asset.AssetListViewHolder
import com.vmedia.feature.eventdetails.presentation.viewholder.asset.item.AssetItemViewHolder

internal class AssetViewHolder(
    context: Context,
    private val onAssetClick: (assetId: Long) -> Unit
) : AssetListViewHolder<AssetInfo, AssetItemViewHolder>(
    context,
    EventType.ASSET,
    R.layout.eventdetails_item_asset_item
) {

    override fun onViewHolderCreate(view: View): AssetItemViewHolder {
        return AssetItemViewHolder(view, ::onClick)
    }

    private fun onClick(position: Int) {
        onAssetClick.invoke(items[position].id)
    }

}