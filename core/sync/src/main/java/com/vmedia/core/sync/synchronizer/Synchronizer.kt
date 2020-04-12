package com.vmedia.core.sync.synchronizer

import com.vmedia.core.sync.SynchronizationDataType
import io.reactivex.Single

interface Synchronizer<T> {

    val dataType: SynchronizationDataType

    fun execute(): Single<T>

}