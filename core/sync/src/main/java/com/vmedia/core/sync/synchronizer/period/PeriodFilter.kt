package com.vmedia.core.sync.synchronizer.period

import com.vmedia.core.common.android.obj.Period
import com.vmedia.core.common.android.obj.isAfter
import com.vmedia.core.common.android.util.Filter
import com.vmedia.core.sync._PeriodLastProvider

internal class PeriodFilter(
    private val periodLastProvider: _PeriodLastProvider
) : Filter<Period> {

    override fun filter(source: List<Period>): List<Period> {
        val lastPeriod = periodLastProvider.invoke()

        return source.filter {
            lastPeriod == null || it.isAfter(lastPeriod) || it == lastPeriod
        }
    }

}