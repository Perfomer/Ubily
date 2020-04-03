package com.vmedia.feature.splash.data

import com.vmedia.core.common.obj.creds.RssToken
import com.vmedia.core.data.datasource.CredentialsDataSource
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.network.datasource.MutableNetworkCredentialsProvider
import io.reactivex.Completable
import io.reactivex.Single

internal class NetworkCredentialsDataSourceImpl(
    private val networkCredentialsProvider: MutableNetworkCredentialsProvider,
    private val credentialsDataSource: CredentialsDataSource,
    private val databaseDataSource: DatabaseDataSource
) : NetworkCredentialsDataSource {

    override fun hasCredentials(): Single<Boolean> {
        return credentialsDataSource.getCredentials().map { !it.isEmpty }
    }

    override fun synchronizeCredentials(): Completable {
        return Completable.mergeArray(
            syncToken(),
            syncPublisher()
        )
    }

    private fun syncToken(): Completable {
        return credentialsDataSource.getCredentials()
            .doOnSuccess { networkCredentialsProvider.token = it.token }
            .ignoreElement()
    }

    private fun syncPublisher(): Completable {
        //todo remove when code below will work
        return Completable.fromAction {
            networkCredentialsProvider.userId = 21232L
            networkCredentialsProvider.rssToken = RssToken(
                publisherName = "wello-graphics",
                token = "ayrtrVzN1cIfk44lSmoJuOFlOSA"
            )
        }

        return databaseDataSource.getPublisher()
            .doOnSuccess { networkCredentialsProvider.userId = it.id }
            .doOnSuccess { networkCredentialsProvider.rssToken = it.rssToken }
            .ignoreElement()
    }

}