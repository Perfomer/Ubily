package com.vmedia.core.sync

import com.vmedia.core.common.obj.Period
import com.vmedia.core.sync.synchronizer.MutableSynchronizationPeriodsProvider

internal object SynchronizationPeriodsProviderImpl : MutableSynchronizationPeriodsProvider {

    override var periods: List<Period> = emptyList()

}