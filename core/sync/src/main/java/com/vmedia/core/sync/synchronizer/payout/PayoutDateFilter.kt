package com.vmedia.core.sync.synchronizer.payout

import com.vmedia.core.common.android.util.Filter
import com.vmedia.core.network.entity.internal.IncomeDto
import com.vmedia.core.sync._PayoutLastDateProvider

internal class PayoutDateFilter(
    private val payoutLastDateProvider: _PayoutLastDateProvider
) : Filter<IncomeDto.Payout> {

    override fun filter(source: List<IncomeDto.Payout>): List<IncomeDto.Payout> {
        val lastCreditDate = payoutLastDateProvider.invoke() ?: return source
        return source.filter { it.date > lastCreditDate }
    }

}