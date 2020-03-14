package com.vmedia.core.sync.synchronizer.revenue

import com.vmedia.core.common.util.Filter
import com.vmedia.core.data.internal.database.entity.Revenue
import com.vmedia.core.sync._LastCreditDateProvider

internal class RevenueFilter(
    private val lastCreditDateProvider: _LastCreditDateProvider
) : Filter<Revenue> {

    override fun filter(source: List<Revenue>): List<Revenue> {
        val lastCreditDate = lastCreditDateProvider.invoke()
        return source.filter { it.date > lastCreditDate }
    }

}