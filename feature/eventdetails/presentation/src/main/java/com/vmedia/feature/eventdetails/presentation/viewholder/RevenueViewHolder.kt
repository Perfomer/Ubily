package com.vmedia.feature.eventdetails.presentation.viewholder

import android.content.Context
import com.vmedia.core.common.android.util.diffedValue
import com.vmedia.core.common.android.util.getString
import com.vmedia.core.common.android.util.setTextColorCompat
import com.vmedia.core.common.android.util.toSpan
import com.vmedia.core.common.pure.obj.EventType
import com.vmedia.core.common.pure.obj.event.RevenueInfo
import com.vmedia.feature.eventdetails.presentation.R
import kotlinx.android.synthetic.main.eventdetails_item_revenue.*

internal class RevenueViewHolder(
    context: Context,
    onRevenueClick: (periodId: Long) -> Unit
) : EventDetailsViewHolder<RevenueInfo>(
    EventType.REVENUE,
    context,
    R.layout.eventdetails_item_revenue
) {

    init {
        feed_item_revenue.setOnClickListener {
            currentItem?.periodId?.let(onRevenueClick::invoke)
        }
    }

    override fun bind(item: RevenueInfo) {
        val delta = item.revenueDelta
        val deltaString = String.format("%.2f", delta)
        val periodString = item.period.getString(context)

        val growthColor = when {
            delta > 0 -> R.color.brand_green_darker
            delta < 0 -> R.color.brand_red
            else -> R.color.brand_grey_darker
        }

        val growthArrow = when {
            delta > 0 -> "↑"
            delta < 0 -> "↓"
            else -> ""
        }

        feed_item_revenue_title.text = context.getString(
            R.string.event_revenue_statistics_text,
            periodString
        ).toSpan()

        feed_item_revenue_growth_value.setTextColorCompat(growthColor)
        feed_item_revenue_growth_value.diffedValue = "$deltaString% $growthArrow"
    }

}