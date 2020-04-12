package com.vmedia.feature.sync.domain

import com.vmedia.core.sync.SynchronizationStatus
import com.vmedia.core.sync.datasource.SynchronizationDataSource
import io.reactivex.Completable
import io.reactivex.Observable

class SyncInteractor(
    private val synchronizationDataSource: SynchronizationDataSource
) {

    fun observeSyncStatus(): Observable<SynchronizationStatus> {
        return synchronizationDataSource.getSynchronizationStatus()
    }

    fun startSync(): Completable {
        return synchronizationDataSource.synchronize()
    }

}