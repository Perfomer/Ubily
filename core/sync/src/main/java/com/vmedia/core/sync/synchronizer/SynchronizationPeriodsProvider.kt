package com.vmedia.core.sync.synchronizer

import com.vmedia.core.common.pure.obj.Period

interface SynchronizationPeriodsProvider {
    val periods: List<Period>
}

interface MutableSynchronizationPeriodsProvider : SynchronizationPeriodsProvider {
    override var periods: List<Period>
}