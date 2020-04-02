package com.vmedia.core.network.filter

import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.Filter
import com.vmedia.core.common.util.sumByBigDecimal
import com.vmedia.core.network.entity.internal.IncomeDto
import com.vmedia.core.network.entity.internal.IncomeDto.Payout
import com.vmedia.core.network.entity.internal.IncomeDto.Revenue

internal object RevenueFilter : Filter<IncomeDto> {

    override fun filter(source: List<IncomeDto>): List<IncomeDto> {
        val revenues = mutableMapOf<Period, MutableSet<Revenue>>()
        val payouts = mutableMapOf<Period, MutableSet<Payout>>()

        source.forEach {
            val period = it.period
            when (it) {
                is Revenue -> {
                    if (!revenues.containsKey(period)) {
                        revenues[period] = mutableSetOf()
                    }

                    revenues[period]!! += it
                }
                is Payout -> {
                    if (!payouts.containsKey(period)) {
                        payouts[period] = mutableSetOf()
                    }

                    payouts[period]!! += it
                }
            }
        }

        val filteredPayouts = payouts.values.map { it.last() }
        val filteredRevenues = revenues.values.map { it.mergeRevenues() }

        return filteredPayouts + filteredRevenues
    }

    private fun Set<Revenue>.mergeRevenues(): Revenue {
        val newest = maxBy(Revenue::date)!!
        val moneyValue = sumByBigDecimal { it.value.value }
        val prototype = first()

        return prototype.copy(
            date = newest.date,
            value = prototype.value.copy(
                value = moneyValue
            )
        )
    }

}