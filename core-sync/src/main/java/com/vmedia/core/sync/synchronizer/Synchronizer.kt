package com.vmedia.core.sync.synchronizer

import com.vmedia.core.sync.SynchronizationEvent
import com.vmedia.core.sync.SynchronizationEventType
import io.reactivex.Single

interface Synchronizer<T : SynchronizationEvent> {

    val eventType: SynchronizationEventType

    fun execute(): Single<T>

}