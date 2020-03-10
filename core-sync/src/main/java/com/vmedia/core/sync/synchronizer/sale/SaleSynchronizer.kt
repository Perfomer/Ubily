package com.vmedia.core.sync.synchronizer.sale

import com.vmedia.core.sync.SynchronizationEvent
import com.vmedia.core.sync.SynchronizationEventType
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single

internal class SaleSynchronizer : Synchronizer<SynchronizationEvent.SalesReceived> {

    override val eventType = SynchronizationEventType.SALES_RECEIVED

    override fun execute(): Single<SynchronizationEvent.SalesReceived> {
        TODO("Not yet implemented")
    }

}