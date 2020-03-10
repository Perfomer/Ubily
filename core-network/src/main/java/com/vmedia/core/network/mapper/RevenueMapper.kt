package com.vmedia.core.network.mapper

import com.vmedia.core.common.obj.Month
import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.FORMAT_TABLEVALUES
import com.vmedia.core.common.util.parse
import com.vmedia.core.network.entity.RevenueEventDto
import com.vmedia.core.network.entity.RevenueEventDto.*

internal object RevenueMapper : TableValuesMapper<RevenueEventDto>() {

    override fun mapItem(tableValues: List<String>): RevenueEventDto {
        val description = tableValues[1]
        val date = tableValues[0].parse(FORMAT_TABLEVALUES)
        val period = description.extractPeriod()

        val debit by lazy { tableValues[2].toMoney()!! }
        val credit by lazy { tableValues[3].toMoney()!! }
        val balance by lazy { tableValues[4].toMoney()!! }
        val isSale by lazy { description.contains("Sale") }

        return when {
            description.contains("Fixing") -> FixingRevenue(date, period, credit, balance, isSale)
            description.contains("Failed") -> FailedPayout(date, period, debit, balance)
            description.contains("Revenue") -> Revenue(date, period, debit, balance, isSale)
            else -> Payout(
                date = date,
                period = period,
                credit = credit,
                autoPayout = description.contains("auto"),
                paypal = description.contains("paypal")
            )
        }
    }

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