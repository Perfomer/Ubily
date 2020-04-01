package com.vmedia.core.sync.synchronizer

import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync.SynchronizationEvent
import io.reactivex.Single

interface Synchronizer<T : SynchronizationEvent> {

    val dataType: SynchronizationDataType

    fun execute(): Single<T>

}