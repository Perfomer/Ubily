package com.vmedia.core.sync.synchronizer.download

import androidx.annotation.WorkerThread
import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.common.util.toFlattenList
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Sale
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync._DownloadMapper
import com.vmedia.core.sync._FreeDownloadsPeriodsProvider
import com.vmedia.core.sync._SaleFilter
import com.vmedia.core.sync.synchronizer.SynchronizationPeriodsProvider
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Observable
import io.reactivex.Single

class DownloadSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val periodsProvider: SynchronizationPeriodsProvider,
    private val freePeriodsProvider: _FreeDownloadsPeriodsProvider,

    private val mapper: _DownloadMapper,
    private val filter: _SaleFilter
) : Synchronizer<List<Sale>> {

    override val dataType = SynchronizationDataType.FREE_DOWNLOADS

    override fun execute(): Single<List<Sale>> {
        return Observable.defer { Observable.fromIterable(extractFreePeriods()).take(2) }
            .flatMapSingle(networkDataSource::getDownloads)
            .toFlattenList()
            .mapWith(mapper)
            .filterWith(filter)
            .actOnSuccess(databaseDataSource::putSales)
    }

    @WorkerThread
    private fun extractFreePeriods(): List<Period> {
        val synchronizationPeriods = periodsProvider.periods

        return freePeriodsProvider.invoke()
            .filter(synchronizationPeriods::contains)
    }

}