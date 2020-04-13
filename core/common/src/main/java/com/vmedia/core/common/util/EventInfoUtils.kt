package com.vmedia.core.common.util

import android.widget.TextView
import com.vmedia.core.common.R
import com.vmedia.core.common.obj.descriptionResource
import com.vmedia.core.common.obj.event.EventInfo
import com.vmedia.core.common.obj.event.EventInfo.*
import com.vmedia.core.common.obj.event.EventInfo.EventListInfo.EventFreeDownload
import com.vmedia.core.common.obj.event.EventInfo.EventListInfo.EventSale
import com.vmedia.core.common.obj.event.SaleInfo
import com.vmedia.core.common.obj.labelResource

fun TextView.setEventDescription(event: EventInfo<*>) {
    val descriptionResource = event.type.descriptionResource

    this.text = when (event) {
        is EventSale -> {
            context.getString(
                descriptionResource,
                event.content.size,
                event.content.sumByBigDecimal(SaleInfo::summaryPrice).toString()
            )
        }

        is EventFreeDownload -> {
            context.getString(
                descriptionResource,
                event.content.size,
                event.content.sumBy(SaleInfo::quantity)
            )
        }

        is EventListInfo.EventAsset -> {
            context.getString(
                descriptionResource,
                event.content.size
            )
        }

        is EventReview -> {
            context.getString(
                descriptionResource,
                event.content.authorName
            )
        }

        is EventPayout -> {
            val payout = event.content
            val period = payout.period
            val monthName = context.getString(period.month.labelResource)

            context.getString(
                descriptionResource,
                payout.amount.toString(),
                monthName,
                period.year
            )
        }

        is EventRevenue -> {
            val revenue = event.content
            val period = revenue.period
            val monthName = context.getString(period.month.labelResource)
            val revenueDescriptionResource =
                if (revenue.sale) R.string.event_revenue_text_sale
                else R.string.event_revenue_text

            context.getString(
                revenueDescriptionResource,
                revenue.amount.toString(),
                monthName,
                period.year
            )
        }

        else -> ""
    }.toSpan()
}