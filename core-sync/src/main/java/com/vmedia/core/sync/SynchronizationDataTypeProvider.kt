package com.vmedia.core.sync

import io.reactivex.Single

interface SynchronizationDataTypeProvider {

    fun shouldSynchronize(type: SynchronizationDataType): Single<Boolean>

}