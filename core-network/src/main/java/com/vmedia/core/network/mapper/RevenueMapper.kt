package com.vmedia.core.network.mapper

import android.annotation.SuppressLint
import com.vmedia.core.common.obj.Month
import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.FORMAT_TABLEVALUES
import com.vmedia.core.common.util.parse
import com.vmedia.core.network.entity.internal.RevenueEventDto
import com.vmedia.core.network.entity.internal.RevenueEventDto.Payout
import com.vmedia.core.network.entity.internal.RevenueEventDto.Revenue

internal object RevenueMapper : TableValuesMapper<RevenueEventDto>() {

    override fun mapItem(tableValues: List<String>): RevenueEventDto {
        val description = tableValues[1]
        val date = tableValues[0].parse(FORMAT_TABLEVALUES)
        val period = description.extractPeriod()

        val debit by lazy { tableValues[2].toMoney()!! }
        val credit by lazy { tableValues[3].toMoney()!! }
        val isSale by lazy { description.contains("Sale") }

        return when {
            description.contains("Fixing") -> Revenue(
                date = date,
                period = period,
                value = credit,
                isSale = isSale
            )

            description.contains("Revenue") -> Revenue(
                date = date,
                period = period,
                value = debit,
                isSale = isSale
            )

            description.contains("Failed") -> Payout(
                date = date,
                period = period,
                value = debit,
                autoPayout = false,
                paypal = false,
                isFailed = true
            )

            else -> Payout(
                date = date,
                period = period,
                value = credit,
                autoPayout = description.contains("auto"),
                paypal = description.contains("paypal"),
                isFailed = false
            )
        }
    }

    @SuppressLint("DefaultLocale")
    private fun String.extractPeriod(): Period {
        val words = split(" ")
        val months = Month.values()

        lateinit var currentMonth: Month
        var currentYear = 1970

        for (word in words) {
            for (month in months) {
                if (word.toUpperCase() == month.name) {
                    currentMonth = month
                } else if (word.matches("^20\\d{2}\$".toRegex())) {
                    currentYear = word.toInt()
                }
            }
        }

        return Period(currentYear, currentMonth)
    }

}