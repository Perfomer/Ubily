package com.vmedia.core.sync.synchronizer.revenue

import com.vmedia.core.common.util.Filter
import com.vmedia.core.network.entity.RevenueEventDto
import com.vmedia.core.sync._LastRevenueDateProvider

internal class RevenueDateFilter(
    private val lastRevenueDateProvider: _LastRevenueDateProvider
) : Filter<RevenueEventDto> {

    override fun filter(source: List<RevenueEventDto>): List<RevenueEventDto> {
        val lastCreditDate = lastRevenueDateProvider.invoke() ?: return source
        return source.filter { it.date > lastCreditDate }
    }

}