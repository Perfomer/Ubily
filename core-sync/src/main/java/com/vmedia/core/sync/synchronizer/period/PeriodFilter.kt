package com.vmedia.core.sync.synchronizer.period

import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.obj.isAfter
import com.vmedia.core.common.util.Filter
import com.vmedia.core.sync._LastPeriodProvider

internal class PeriodFilter(
    private val lastPeriodProvider: _LastPeriodProvider
) : Filter<Period> {

    override fun filter(source: List<Period>): List<Period> {
        val lastPeriod = lastPeriodProvider.invoke()

        return source.filter {
            lastPeriod == null || it.isAfter(lastPeriod) || it == lastPeriod
        }
    }

}