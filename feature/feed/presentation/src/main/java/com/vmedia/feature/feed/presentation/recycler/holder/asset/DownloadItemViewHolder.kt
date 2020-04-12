package com.vmedia.feature.feed.presentation.recycler.holder.asset

import android.view.View
import androidx.core.view.isVisible
import com.vmedia.core.common.obj.descriptionResource
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.loadRoundedCorners
import com.vmedia.core.common.util.toSpan
import com.vmedia.core.data.obj.EventInfo.EventListInfo.EventFreeDownload
import com.vmedia.core.data.obj.SaleInfo
import com.vmedia.feature.feed.presentation.recycler.holder.AssetListViewHolder
import com.vmedia.feature.feed.presentation.recycler.holder.ItemViewHolder
import kotlinx.android.synthetic.main.feed_item.*
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

    override fun bindContent(item: EventFreeDownload) {
        super.bindContent(item)

        feed_item_description.text = context.getString(
            item.type.descriptionResource,
            item.content.size,
            item.content.sumBy(SaleInfo::quantity)
        ).toSpan()
    }

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
        feed_item_sale_icon.loadRoundedCorners(content.assetIcon)
    }

}