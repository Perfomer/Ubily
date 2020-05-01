package com.vmedia.core.sync.synchronizer.sale

import com.vmedia.core.common.android.util.actOnSuccess
import com.vmedia.core.common.android.util.filterWith
import com.vmedia.core.common.android.util.mapWith
import com.vmedia.core.common.android.util.toFlattenList
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Sale
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync._SaleFilter
import com.vmedia.core.sync._SaleMapper
import com.vmedia.core.sync.synchronizer.SynchronizationPeriodsProvider
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Observable
import io.reactivex.Single

class SaleSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,
    private val periodsProvider: SynchronizationPeriodsProvider,

    private val mapper: _SaleMapper,
    private val filter: _SaleFilter
) : Synchronizer<List<Sale>> {

    override val dataType = SynchronizationDataType.SALES

    override fun execute(): Single<List<Sale>> {
        return Observable.defer { Observable.fromIterable(periodsProvider.periods).take(2) }
            .flatMapSingle(networkDataSource::getSales)
            .toFlattenList()
            .mapWith(mapper)
            .filterWith(filter)
            .actOnSuccess(databaseDataSource::putSales)
    }

}