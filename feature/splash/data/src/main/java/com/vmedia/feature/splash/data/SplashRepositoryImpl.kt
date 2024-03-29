package com.vmedia.feature.splash.data

import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.datasource.PublisherDataSource
import com.vmedia.feature.splash.domain.SplashRepository
import io.reactivex.Completable
import io.reactivex.Single

internal class SplashRepositoryImpl(
    private val databaseDataSource: DatabaseDataSource,
    private val networkCredentialsDataSource: NetworkCredentialsDataSource,
    private val publisherDataSource: PublisherDataSource
) : SplashRepository {

    override fun syncNetworkCredentials(): Completable {
        return networkCredentialsDataSource.synchronizeCredentials()
            .onErrorComplete()
    }

    override fun isUserAuthorized(): Single<Boolean> {
        return networkCredentialsDataSource.hasCredentials()
    }

    override fun isSynchronizationSucceedAtLeastOnce(): Single<Boolean> {
        return databaseDataSource.hasEvents()
    }

    override fun isOnboardingAlreadyShown(): Single<Boolean> {
        return publisherDataSource.isOnboardingAlreadyShown()
    }

}