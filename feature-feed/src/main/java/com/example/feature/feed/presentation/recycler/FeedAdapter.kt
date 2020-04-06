package com.example.feature.feed.presentation.recycler

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.example.feature.feed.R
import com.example.feature.feed.presentation.recycler.holder.*
import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.EventType.*
import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.inflate
import com.vmedia.core.common.view.recycler.base.BaseAdapter
import com.vmedia.core.common.view.recycler.diffedListBy
import com.vmedia.core.data.obj.EventInfo
import kotlinx.android.synthetic.main.feed_item.view.*

internal class FeedAdapter(
    private val onItemClick: (item: EventInfo) -> Unit,
    private val onAssetClick: (assetId: Long) -> Unit,
    private val onRevenueClick: (periodRevenue: Period) -> Unit
) : BaseAdapter<FeedViewHolder<out EventInfo>>() {

    var items by diffedListBy(EventInfo::id)

    private val types = values()


    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].type.ordinal

    override fun onLayoutRequested(viewType: Int) = R.layout.feed_item

    override fun onCreateViewHolder(view: View, viewType: Int): FeedViewHolder<out EventInfo> {
        val eventType = types[viewType]
        val viewGroup = view as ViewGroup

        onContentLayoutRequested(eventType)?.let {
            viewGroup.attachViewHolderContent(it)
        }

        return when (eventType) {
            SALE -> SaleViewHolder(view, ::onClick, ::onAssetClick)
            FREE_DOWNLOAD -> DownloadViewHolder(view, ::onClick, ::onAssetClick)
            REVIEW -> ReviewViewHolder(view, ::onClick, ::onReviewAssetClick)
            ASSET -> AssetViewHolder(view, ::onClick, ::onAssetClick)
            PAYOUT -> PayoutViewHolder(view, ::onClick)
            REVENUE -> RevenueViewHolder(view, ::onClick, ::onRevenueClick)
            else -> TODO()
        }
    }

    override fun onBindViewHolder(holder: FeedViewHolder<out EventInfo>, position: Int) {
        holder.bind(items[position])
    }


    private fun onContentLayoutRequested(eventType: EventType): Int? {
        return when (eventType) {
            SALE -> R.layout.feed_item_sale
            FREE_DOWNLOAD -> R.layout.feed_item_sale
            REVIEW -> R.layout.feed_item_review
            ASSET -> R.layout.feed_item_asset
            PAYOUT -> R.layout.feed_item_payout
            REVENUE -> R.layout.feed_item_revenue
            else -> TODO()
        }
    }


    private fun onClick(position: Int) {
        onItemClick.invoke(items[position])
    }

    private fun onAssetClick(position: Int, assetIndex: Int) {
        val assetId = when(val item = items[position]) {
            is EventInfo.EventAsset -> item.assets[assetIndex].id
            is EventInfo.EventSale -> item.sales[assetIndex].assetId
            is EventInfo.EventFreeDownload -> item.downloads[assetIndex].assetId
            else -> throw IllegalArgumentException()
        }

        onAssetClick.invoke(assetId)
    }

    private fun onReviewAssetClick(position: Int) {
        val eventInfo = items[position] as EventInfo.EventReview
        onAssetClick.invoke(eventInfo.review.assetId)
    }

    private fun onRevenueClick(position: Int) {
        val revenueItem = items[position] as EventInfo.EventRevenue
        onRevenueClick.invoke(revenueItem.revenue.period)
    }


    private companion object {

        fun ViewGroup.attachViewHolderContent(@LayoutRes contentLayoutResource: Int) {
            val contentView = contentLayoutResource.let { context.inflate(it) }
            feed_item_content.addView(contentView)
        }

    }

}