package com.vmedia.core.sync.synchronizer.revenue

import com.vmedia.core.common.util.Filter
import com.vmedia.core.network.entity.RevenueEventDto
import com.vmedia.core.sync._LastCreditDateProvider

internal class RevenueDateFilter(
    private val lastCreditDateProvider: _LastCreditDateProvider
) : Filter<RevenueEventDto> {

    override fun filter(source: List<RevenueEventDto>): List<RevenueEventDto> {
        val lastCreditDate = lastCreditDateProvider.invoke()
        return source.filter { it.date > lastCreditDate }
    }

}