package com.vmedia.core.sync.synchronizer.download

import androidx.annotation.WorkerThread
import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.common.util.toFlattenList
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.*
import com.vmedia.core.sync.SynchronizationEvent.FreeDownloadsReceived
import com.vmedia.core.sync.synchronizer.SynchronizationPeriodsProvider
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Observable
import io.reactivex.Single

class DownloadSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val periodsProvider: SynchronizationPeriodsProvider,
    private val freePeriodsProvider: _FreeDownloadsPeriodsProvider,

    private val filter: _SaleFilter,
    private val mapper: _DownloadMapper
) : Synchronizer<FreeDownloadsReceived> {

    override val dataType = SynchronizationDataType.FREE_DOWNLOADS

    override fun execute(): Single<FreeDownloadsReceived> {
        return Observable.defer { Observable.fromIterable(extractFreePeriods()) }
            .flatMapSingle(networkDataSource::getDownloads)
            .toFlattenList()
            .mapWith(mapper)
            .filterWith(filter)
            .actOnSuccess(databaseDataSource::putSales)
            .map(SynchronizationEvent::FreeDownloadsReceived)
    }

    @WorkerThread
    private fun extractFreePeriods(): List<Period> {
        val synchronizationPeriods = periodsProvider.periods

        return freePeriodsProvider.invoke()
            .filter(synchronizationPeriods::contains)
    }

}