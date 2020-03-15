package com.vmedia.core.sync.synchronizer.payout

import com.vmedia.core.common.util.Filter
import com.vmedia.core.network.entity.RevenueEventDto
import com.vmedia.core.sync._LastPayoutDateProvider

internal class PayoutDateFilter(
    private val lastPayoutDateProvider: _LastPayoutDateProvider
) : Filter<RevenueEventDto> {

    override fun filter(source: List<RevenueEventDto>): List<RevenueEventDto> {
        val lastCreditDate = lastPayoutDateProvider.invoke()
        return source.filter { it.date > lastCreditDate }
    }

}