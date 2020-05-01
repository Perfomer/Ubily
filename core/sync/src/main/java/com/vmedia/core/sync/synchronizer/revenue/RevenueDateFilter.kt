package com.vmedia.core.sync.synchronizer.revenue

import com.vmedia.core.common.pure.util.Filter
import com.vmedia.core.network.entity.internal.IncomeDto
import com.vmedia.core.sync._RevenueLastDateProvider

internal class RevenueDateFilter(
    private val revenueLastDateProvider: _RevenueLastDateProvider
) : Filter<IncomeDto.Revenue> {

    override fun filter(source: List<IncomeDto.Revenue>): List<IncomeDto.Revenue> {
        val lastCreditDate = revenueLastDateProvider.invoke() ?: return source
        return source.filter { it.date > lastCreditDate }
    }

}