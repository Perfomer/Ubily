package com.vmedia.feature.feed.presentation.recycler.holder

import android.view.View
import com.vmedia.core.common.obj.event.EventInfo.EventRevenue
import com.vmedia.core.common.obj.getString
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.setTextColorCompat
import com.vmedia.core.common.util.toSpan
import com.vmedia.feature.feed.presentation.R
import com.vmedia.feature.feed.presentation.recycler.FeedViewHolder
import kotlinx.android.synthetic.main.feed_item_revenue.*

internal class RevenueViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit,
    onOptionsClick: (position: Int) -> Unit,
    onRevenueClick: (position: Int) -> Unit
) : FeedViewHolder<EventRevenue>(containerView, onClick, onOptionsClick) {

    init {
        feed_item_revenue.setOnClickListener { onRevenueClick.safeInvoke(adapterPosition) }
    }

    override fun bindContent(item: EventRevenue) {
        val revenue = item.content

        val delta = revenue.revenueDelta
        val deltaString = String.format("%.2f", delta)
        val periodString = revenue.period.getString(context)

        val growthColor = when {
            delta > 0 -> R.color.brand_green_darker
            delta < 0 -> R.color.brand_red
            else -> R.color.brand_grey_dark
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