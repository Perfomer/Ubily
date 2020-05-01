package com.vmedia.feature.eventdetails.presentation.viewholder

import android.content.Context
import android.view.View
import com.vmedia.core.common.pure.obj.EventType
import com.vmedia.core.common.pure.obj.event.SaleInfo
import com.vmedia.feature.eventdetails.presentation.R
import com.vmedia.feature.eventdetails.presentation.viewholder.asset.AssetListViewHolder
import com.vmedia.feature.eventdetails.presentation.viewholder.asset.item.SaleItemViewHolder

internal class SaleViewHolder(
    context: Context,
    private val onAssetClick: (assetId: Long) -> Unit
) : AssetListViewHolder<SaleInfo, SaleItemViewHolder>(
    context,
    EventType.SALE,
    R.layout.eventdetails_item_sale_item
) {

    override fun onViewHolderCreate(view: View): SaleItemViewHolder {
        return SaleItemViewHolder(view, ::onClick)
    }

    private fun onClick(position: Int) {
        onAssetClick.invoke(items[position].assetId)
    }

}