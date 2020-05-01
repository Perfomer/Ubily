package com.vmedia.core.sync.datasource

import com.vmedia.core.common.pure.obj.Period
import com.vmedia.core.sync.synchronizer.MutableSynchronizationPeriodsProvider

internal object SynchronizationPeriodsProviderImpl : MutableSynchronizationPeriodsProvider {

    override var periods: List<Period> = emptyList()

}