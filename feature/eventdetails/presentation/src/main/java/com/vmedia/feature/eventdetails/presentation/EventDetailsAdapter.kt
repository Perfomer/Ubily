package com.vmedia.feature.eventdetails.presentation

import android.view.ViewGroup
import com.vmedia.core.common.android.obj.EventType
import com.vmedia.core.common.android.obj.EventType.*
import com.vmedia.core.common.android.obj.event.EventInfo
import com.vmedia.feature.eventdetails.presentation.viewholder.*

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
            SALE -> SaleViewHolder(context, onAssetClick)
            FREE_DOWNLOAD -> DownloadViewHolder(context, onAssetClick)
            REVIEW -> ReviewViewHolder(context, onAssetClick)
            ASSET -> AssetViewHolder(context, onAssetClick)
            PAYOUT -> PayoutViewHolder(context)
            REVENUE -> RevenueViewHolder(context, onRevenueClick)
            else -> null
        }
    }

}