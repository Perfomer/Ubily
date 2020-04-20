package com.vmedia.feature.feed.presentation.recycler.holder.asset

import android.view.View
import androidx.core.view.isVisible
import com.vmedia.core.common.obj.event.EventInfo.EventListInfo.EventFreeDownload
import com.vmedia.core.common.obj.event.SaleInfo
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.loadRoundedImage
import com.vmedia.feature.feed.presentation.recycler.holder.AssetListViewHolder
import com.vmedia.feature.feed.presentation.recycler.holder.ItemViewHolder
import kotlinx.android.synthetic.main.feed_item_sale_item.*

internal class DownloadViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit,
    onOptionsClick: (position: Int) -> Unit,
    onAssetClick: (position: Int, assetIndex: Int) -> Unit
) : AssetListViewHolder<SaleInfo, EventFreeDownload, DownloadItemViewHolder>(
    containerView,
    onClick,
    onOptionsClick,
    onAssetClick
) {

    override fun onItemViewHolderCreate(view: View) = DownloadItemViewHolder(view)

}

internal class DownloadItemViewHolder(
    containerView: View
) : ItemViewHolder<SaleInfo>(containerView) {

    init {
        feed_item_sale_price.isVisible = false
    }

    override fun bind(content: SaleInfo) {
        feed_item_sale_price.diffedValue = "$${content.summaryPrice}"
        feed_item_sale_quantity.diffedValue = "x${content.quantity}"
        feed_item_sale_title.diffedValue = content.assetName
        feed_item_sale_icon.loadRoundedImage(content.assetIcon)
    }

}