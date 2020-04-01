package com.vmedia.core.sync.datasource

import com.vmedia.core.sync.SynchronizationDataType
import io.reactivex.Single

interface SynchronizationDataTypeProvider {

    fun shouldSynchronize(type: SynchronizationDataType): Single<Boolean>

}