package com.vmedia.core.sync.synchronizer.revenue

import com.vmedia.core.common.util.Filter
import com.vmedia.core.network.entity.internal.RevenueEventDto
import com.vmedia.core.sync._LastRevenueDateProvider

internal class RevenueDateFilter(
    private val lastRevenueDateProvider: _LastRevenueDateProvider
) : Filter<RevenueEventDto.Revenue> {

    override fun filter(source: List<RevenueEventDto.Revenue>): List<RevenueEventDto.Revenue> {
        val lastCreditDate = lastRevenueDateProvider.invoke() ?: return source
        return source.filter { it.date > lastCreditDate }
    }

}