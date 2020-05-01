package com.vmedia.core.sync.synchronizer

import com.vmedia.core.common.android.obj.Period

interface SynchronizationPeriodsProvider {
    val periods: List<Period>
}

interface MutableSynchronizationPeriodsProvider : SynchronizationPeriodsProvider {
    override var periods: List<Period>
}