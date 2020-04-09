package com.example.feature.feed.presentation.recycler.holder

import android.view.View
import com.example.feature.feed.R
import com.example.feature.feed.presentation.recycler.FeedViewHolder
import com.vmedia.core.common.obj.getString
import com.vmedia.core.common.obj.labelResource
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.setTextColorCompat
import com.vmedia.core.common.util.toSpan
import com.vmedia.core.data.obj.EventInfo.EventRevenue
import kotlinx.android.synthetic.main.feed_item.*
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
        val period = revenue.period
        val periodString = period.getString(context)
        val monthName = getString(period.month.labelResource)

        val descriptionResource =
            if (revenue.sale) R.string.event_revenue_text_sale
            else R.string.event_revenue_text

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

        feed_item_description.text = context.getString(
            descriptionResource,
            revenue.amount.toString(),
            monthName,
            period.year
        ).toSpan()

        feed_item_revenue_title.text = context.getString(
            R.string.event_revenue_statistics_text,
            periodString
        )

        feed_item_revenue_growth_value.setTextColorCompat(growthColor)
        feed_item_revenue_growth_value.diffedValue = "$delta% $growthArrow"
    }

}