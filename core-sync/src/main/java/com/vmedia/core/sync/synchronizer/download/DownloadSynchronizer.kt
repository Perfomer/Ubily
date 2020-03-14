package com.vmedia.core.sync.synchronizer.download

import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.common.util.toFlattenList
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationEvent
import com.vmedia.core.sync.SynchronizationEvent.FreeDownloadsReceived
import com.vmedia.core.sync.SynchronizationEventType
import com.vmedia.core.sync._DownloadMapper
import com.vmedia.core.sync._SaleFilter
import com.vmedia.core.sync.synchronizer.SynchronizationPeriodsProvider
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Observable
import io.reactivex.Single

internal class DownloadSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,
    private val periodsProvider: SynchronizationPeriodsProvider,

    private val filter: _SaleFilter,
    private val mapper: _DownloadMapper
) : Synchronizer<FreeDownloadsReceived> {

    override val eventType = SynchronizationEventType.FREE_DOWNLOADS_RECEIVED

    override fun execute(): Single<FreeDownloadsReceived> {
        return Observable.fromIterable(periodsProvider.periods)
            .flatMapSingle(networkDataSource::getDownloads)
            .toFlattenList()
            .mapWith(mapper)
            .filterWith(filter)
            .actOnSuccess(databaseDataSource::putSales)
            .map(SynchronizationEvent::FreeDownloadsReceived)
    }

}