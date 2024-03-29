package com.vmedia.feature.sync.domain

import com.vmedia.core.data.datasource.CredentialsDataSource
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.sync.SynchronizationStatus
import com.vmedia.core.sync.datasource.SynchronizationDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class SyncInteractor(
    private val synchronizationDataSource: SynchronizationDataSource,
    private val credentialsDataSource: CredentialsDataSource,
    private val databaseDataSource: DatabaseDataSource,
) {

    fun isSynchronizationSucceedAtLeastOnce(): Single<Boolean> {
        return databaseDataSource.hasEvents()
    }

    fun observeSyncStatus(): Observable<SynchronizationStatus> {
        return synchronizationDataSource.getSynchronizationStatus()
    }

    fun dropCredentials(): Completable {
        return credentialsDataSource.dropCredentials()
    }
}
