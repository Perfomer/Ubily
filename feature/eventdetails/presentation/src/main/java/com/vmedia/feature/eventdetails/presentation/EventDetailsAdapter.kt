package com.vmedia.feature.eventdetails.presentation

import android.view.ViewGroup
import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.EventType.*
import com.vmedia.core.common.obj.event.AssetInfo
import com.vmedia.core.common.obj.event.EventInfo
import com.vmedia.feature.eventdetails.presentation.viewholder.EventDetailsViewHolder
import com.vmedia.feature.eventdetails.presentation.viewholder.asset.AssetViewHolder

internal class EventDetailsAdapter(
    private val parent: ViewGroup,
    private val onAssetClick: (assetId: Long) -> Unit
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
        return when (eventType) {
            SALE -> TODO()
            FREE_DOWNLOAD -> TODO()
            REVIEW -> TODO()
            ASSET -> AssetViewHolder(parent.context, ::onAssetClick)
            PAYOUT -> TODO()
            REVENUE -> TODO()
            else -> null
        }
    }

    private fun onAssetClick(asset: AssetInfo) {
        onAssetClick.invoke(asset.id)
    }

}