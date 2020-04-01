package com.vmedia.core.sync.synchronizer.payout

import com.vmedia.core.common.util.Filter
import com.vmedia.core.network.entity.internal.RevenueEventDto
import com.vmedia.core.sync._LastPayoutDateProvider

internal class PayoutDateFilter(
    private val lastPayoutDateProvider: _LastPayoutDateProvider
) : Filter<RevenueEventDto.Payout> {

    override fun filter(source: List<RevenueEventDto.Payout>): List<RevenueEventDto.Payout> {
        val lastCreditDate = lastPayoutDateProvider.invoke() ?: return source
        return source.filter { it.date > lastCreditDate }
    }

}