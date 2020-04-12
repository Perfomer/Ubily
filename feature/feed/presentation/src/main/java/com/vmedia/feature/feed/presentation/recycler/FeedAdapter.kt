package com.vmedia.feature.feed.presentation.recycler

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.EventType.*
import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.inflate
import com.vmedia.core.common.view.recycler.base.BaseAdapter
import com.vmedia.core.common.view.recycler.diffedListBy
import com.vmedia.core.data.obj.EventInfo
import com.vmedia.core.data.obj.EventInfo.EventListInfo.*
import com.vmedia.core.data.obj.EventInfo.EventRevenue
import com.vmedia.core.data.obj.EventInfo.EventReview
import com.vmedia.feature.feed.presentation.R
import com.vmedia.feature.feed.presentation.recycler.holder.PayoutViewHolder
import com.vmedia.feature.feed.presentation.recycler.holder.RevenueViewHolder
import com.vmedia.feature.feed.presentation.recycler.holder.ReviewViewHolder
import com.vmedia.feature.feed.presentation.recycler.holder.asset.AssetViewHolder
import com.vmedia.feature.feed.presentation.recycler.holder.asset.DownloadViewHolder
import com.vmedia.feature.feed.presentation.recycler.holder.asset.SaleViewHolder
import kotlinx.android.synthetic.main.feed_item.view.*

internal class FeedAdapter(
    private val onItemClick: (item: EventInfo<*>) -> Unit,
    private val onOptionsClick: (item: EventInfo<*>) -> Unit,
    private val onAssetClick: (assetId: Long) -> Unit,
    private val onRevenueClick: (periodRevenue: Period) -> Unit
) : BaseAdapter<FeedViewHolder<out EventInfo<*>>>() {

    var items by diffedListBy(EventInfo<*>::id)

    private val types = values()


    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].type.ordinal

    override fun onLayoutRequested(viewType: Int) = R.layout.feed_item

    override fun onCreateViewHolder(view: View, viewType: Int): FeedViewHolder<out EventInfo<*>> {
        val eventType = types[viewType]
        val viewGroup = view as ViewGroup

        onContentLayoutRequested(eventType)?.let {
            viewGroup.attachViewHolderContent(it)
        }

        return when (eventType) {
            SALE -> SaleViewHolder(view, ::onClick, ::onOptionsClick, ::onAssetClick)
            FREE_DOWNLOAD -> DownloadViewHolder(view, ::onClick, ::onOptionsClick, ::onAssetClick)
            REVIEW -> ReviewViewHolder(view, ::onClick, ::onOptionsClick, ::onReviewAssetClick)
            ASSET -> AssetViewHolder(view, ::onClick, ::onOptionsClick, ::onAssetClick)
            PAYOUT -> PayoutViewHolder(view, ::onOptionsClick, ::onClick)
            REVENUE -> RevenueViewHolder(view, ::onClick, ::onOptionsClick, ::onRevenueClick)
            else -> TODO()
        }
    }

    override fun onBindViewHolder(holder: FeedViewHolder<out EventInfo<*>>, position: Int) {
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

    private fun onOptionsClick(position: Int) {
        onOptionsClick.invoke(items[position])
    }

    private fun onAssetClick(position: Int, assetIndex: Int) {
        val assetId = when (val item = items[position]) {
            is EventAsset -> item.content[assetIndex].id
            is EventSale -> item.content[assetIndex].assetId
            is EventFreeDownload -> item.content[assetIndex].assetId
            else -> throw IllegalArgumentException()
        }

        onAssetClick.invoke(assetId)
    }

    private fun onReviewAssetClick(position: Int) {
        val eventInfo = items[position] as EventReview
        onAssetClick.invoke(eventInfo.content.assetId)
    }

    private fun onRevenueClick(position: Int) {
        val revenueItem = items[position] as EventRevenue
        onRevenueClick.invoke(revenueItem.content.period)
    }


    private companion object {

        fun ViewGroup.attachViewHolderContent(@LayoutRes contentLayoutResource: Int) {
            val contentView = contentLayoutResource.let { context.inflate(it) }
            feed_item_content.addView(contentView)
        }

    }

}