package com.vmedia.core.network.mapper

import com.vmedia.core.common.util.FORMAT_TABLEVALUES
import com.vmedia.core.common.util.parse
import com.vmedia.core.network.api.entity.RevenueEventDto
import com.vmedia.core.network.api.entity.RevenueEventDto.*

internal object RevenueMapper : TableValuesMapper<RevenueEventDto>() {

    override fun mapItem(tableValues: List<String>): RevenueEventDto {
        val description = tableValues[1]
        val date = tableValues[0].parse(FORMAT_TABLEVALUES)

        val debit by lazy { tableValues[2].toMoney()!! }
        val credit by lazy { tableValues[3].toMoney()!! }
        val balance by lazy { tableValues[4].toMoney()!! }
        val isSale by lazy { description.contains("Sale") }

        return when {
            description.contains("Fixing") -> FixingRevenue(date, credit, balance, isSale)
            description.contains("Failed") -> FailedPayout(date, debit, balance)
            description.contains("Revenue") -> Revenue(date, debit, balance, isSale)
            else -> Payout(
                date = date,
                credit = credit,
                autoPayout = description.contains("auto"),
                paypal = description.contains("paypal")
            )
        }
    }

}