package com.vmedia.core.sync.synchronizer.publisher

import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Publisher
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync._PublisherMapper
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith

class PublisherSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val mapper: _PublisherMapper
) : Synchronizer<Publisher> {

    override val dataType = SynchronizationDataType.PUBLISHER

    override fun execute(): Single<Publisher> {
        return networkDataSource.getPublisherId()
            .zipWith(networkDataSource.getPublisherInfo())
            .mapWith(mapper)
            .actOnSuccess(databaseDataSource::putPublisher)
    }

}