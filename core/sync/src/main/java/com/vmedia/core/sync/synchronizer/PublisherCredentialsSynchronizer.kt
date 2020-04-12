package com.vmedia.core.sync.synchronizer

import com.vmedia.core.network.datasource.MutableNetworkCredentialsProvider
import com.vmedia.core.network.datasource.NetworkDataSource
import io.reactivex.Completable

internal class PublisherCredentialsSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val credentials: MutableNetworkCredentialsProvider
) {

    fun synchronize(): Completable {
        return networkDataSource.getPublisherId()
            .doOnSuccess { credentials.userId = it }
            .flatMap { networkDataSource.getPublisherInfo() }
            .doOnSuccess { credentials.rssToken = it.rssToken }
            .ignoreElement()
    }

}