package com.vmedia.feature.feed.presentation.recycler.holder.asset

import android.view.View
import com.vmedia.core.common.android.obj.event.EventInfo.EventListInfo.EventSale
import com.vmedia.core.common.android.obj.event.SaleInfo
import com.vmedia.core.common.android.util.diffedValue
import com.vmedia.core.common.android.util.loadRoundedImage
import com.vmedia.feature.feed.presentation.recycler.holder.AssetListViewHolder
import com.vmedia.feature.feed.presentation.recycler.holder.ItemViewHolder
import kotlinx.android.synthetic.main.feed_item_sale_item.*

internal class SaleViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit,
    onOptionsClick: (position: Int) -> Unit,
    onAssetClick: (position: Int, assetIndex: Int) -> Unit
) : AssetListViewHolder<SaleInfo, EventSale, SaleItemViewHolder>(
    containerView,
    onClick,
    onOptionsClick,
    onAssetClick
) {

    override fun onItemViewHolderCreate(view: View) = SaleItemViewHolder(view)

}

internal class SaleItemViewHolder(
    containerView: View
) : ItemViewHolder<SaleInfo>(containerView) {

    override fun bind(content: SaleInfo) {
        feed_item_sale_price.diffedValue = "$${content.summaryPrice}"
        feed_item_sale_quantity.diffedValue = "x${content.quantity}"
        feed_item_sale_title.diffedValue = content.assetName
        feed_item_sale_icon.loadRoundedImage(content.assetIcon)
    }

}