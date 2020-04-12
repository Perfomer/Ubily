package com.vmedia.core.sync.datasource

import com.vmedia.core.sync.SynchronizationStatus
import io.reactivex.Completable
import io.reactivex.Observable

interface SynchronizationDataSource {

    val isSynchronizing: Boolean

    fun synchronize(): Completable

    fun getSynchronizationStatus(): Observable<SynchronizationStatus>

}