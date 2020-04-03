package com.vmedia.feature.splash.data

import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.feature.splash.domain.SplashRepository
import io.reactivex.Completable
import io.reactivex.Single

internal class SplashRepositoryImpl(
    private val databaseDataSource: DatabaseDataSource,
    private val networkCredentialsDataSource: NetworkCredentialsDataSource
) : SplashRepository {

    override fun syncNetworkCredentials(): Completable {
        return networkCredentialsDataSource.synchronizeCredentials()
    }

    override fun isUserAuthorized(): Single<Boolean> {
        return networkCredentialsDataSource.hasCredentials()
    }

    override fun isSynchronizationSucceedAtLeastOnce(): Single<Boolean> {
        return databaseDataSource.hasEvents()
    }

}