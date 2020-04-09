package com.example.feature.feed.presentation.recycler.holder.asset

import android.view.View
import com.example.feature.feed.presentation.recycler.holder.AssetListViewHolder
import com.example.feature.feed.presentation.recycler.holder.ItemViewHolder
import com.vmedia.core.common.obj.descriptionResource
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.loadRoundedCorners
import com.vmedia.core.common.util.sumByBigDecimal
import com.vmedia.core.common.util.toSpan
import com.vmedia.core.data.obj.EventInfo.EventListInfo.EventSale
import com.vmedia.core.data.obj.SaleInfo
import kotlinx.android.synthetic.main.feed_item.*
import kotlinx.android.synthetic.main.feed_item_sale_item.*

internal class SaleViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit,
    onAssetClick: (position: Int, assetIndex: Int) -> Unit
) : AssetListViewHolder<SaleInfo, EventSale, SaleItemViewHolder>(
    containerView,
    onClick,
    onAssetClick
) {

    override fun bindContent(item: EventSale) {
        super.bindContent(item)

        feed_item_description.text = context.getString(
            item.type.descriptionResource,
            item.content.size,
            item.content.sumByBigDecimal(SaleInfo::summaryPrice).toString()
        ).toSpan()
    }

    override fun onItemViewHolderCreate(view: View) =
        SaleItemViewHolder(
            view
        )

}

internal class SaleItemViewHolder(
    containerView: View
) : ItemViewHolder<SaleInfo>(containerView) {

    override fun bind(content: SaleInfo) {
        feed_item_sale_price.diffedValue = "$${content.summaryPrice}"
        feed_item_sale_quantity.diffedValue = "x${content.quantity}"
        feed_item_sale_title.diffedValue = content.assetName
        feed_item_sale_icon.loadRoundedCorners(content.assetIcon)
    }

}