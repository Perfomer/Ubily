package com.vmedia.feature.eventdetails.presentation.viewholder.asset.item

import android.view.View
import com.vmedia.core.common.obj.event.SaleInfo
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.loadRoundedImage
import com.vmedia.feature.eventdetails.presentation.viewholder.asset.recycler.BaseAssetItemViewHolder
import kotlinx.android.synthetic.main.eventdetails_item_sale_item.*

internal class SaleItemViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit
) : BaseAssetItemViewHolder<SaleInfo>(containerView, onClick) {

    override fun bind(item: SaleInfo) {
        super.bind(item)
        eventdetails_item_sale_price.diffedValue = "$${item.summaryPrice}"
        eventdetails_item_sale_quantity.diffedValue = "x${item.quantity}"
        eventdetails_item_sale_title.diffedValue = item.assetName
        eventdetails_item_sale_icon.loadRoundedImage(item.assetIcon)
    }

}