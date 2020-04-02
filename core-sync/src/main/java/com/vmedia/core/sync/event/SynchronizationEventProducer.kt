package com.vmedia.core.sync.event

import com.vmedia.core.sync.SynchronizationStatus
import io.reactivex.Completable

interface SynchronizationEventProducer {

    fun produce(status: SynchronizationStatus): Completable

}