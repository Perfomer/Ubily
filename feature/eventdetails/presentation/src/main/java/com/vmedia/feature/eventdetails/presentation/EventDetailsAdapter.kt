package com.vmedia.feature.eventdetails.presentation

import android.view.ViewGroup
import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.EventType.*
import com.vmedia.core.common.obj.event.EventInfo
import com.vmedia.feature.eventdetails.presentation.viewholder.EventDetailsViewHolder
import com.vmedia.feature.eventdetails.presentation.viewholder.asset.AssetViewHolder
import com.vmedia.feature.eventdetails.presentation.viewholder.payout.PayoutViewHolder
import com.vmedia.feature.eventdetails.presentation.viewholder.revenue.RevenueViewHolder
import com.vmedia.feature.eventdetails.presentation.viewholder.review.ReviewViewHolder

internal class EventDetailsAdapter(
    private val parent: ViewGroup,
    private val onAssetClick: (assetId: Long) -> Unit,
    private val onRevenueClick: (periodId: Long) -> Unit
) {

    private var viewHolder: EventDetailsViewHolder<*>? = null

    fun bind(item: EventInfo<*>) {
        val type = item.type

        if (viewHolder?.eventType != type) {
            viewHolder = onViewHolderCreate(type)
            parent.removeAllViews()
            viewHolder?.containerView?.let(parent::addView)
        }

        viewHolder?.bindEvent(item)
    }

    private fun onViewHolderCreate(eventType: EventType): EventDetailsViewHolder<*>? {
        val context = parent.context

        return when (eventType) {
            SALE -> TODO()
            FREE_DOWNLOAD -> TODO()
            REVIEW -> ReviewViewHolder(context, onAssetClick)
            ASSET -> AssetViewHolder(context, onAssetClick)
            PAYOUT -> PayoutViewHolder(context)
            REVENUE -> RevenueViewHolder(context, onRevenueClick)
            else -> null
        }
    }

}