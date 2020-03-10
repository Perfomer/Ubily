package com.vmedia.core.sync.synchronizer

import com.vmedia.core.common.obj.Period

interface SynchronizationPeriodsProvider {
    val periods: List<Period>
}