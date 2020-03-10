package com.vmedia.core.sync.synchronizer.sale

import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.common.util.toFlattenList
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationEvent.SalesReceived
import com.vmedia.core.sync.SynchronizationEventType
import com.vmedia.core.sync._SaleFilter
import com.vmedia.core.sync._SaleMapper
import com.vmedia.core.sync.synchronizer.SynchronizationPeriodsProvider
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Observable
import io.reactivex.Single

internal class SaleSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,
    private val periodsProvider: SynchronizationPeriodsProvider,

    private val filter: _SaleFilter,
    private val mapper: _SaleMapper
) : Synchronizer<SalesReceived> {

    override val eventType = SynchronizationEventType.SALES_RECEIVED

    override fun execute(): Single<SalesReceived> {
        return Observable.fromIterable(periodsProvider.periods)
            .flatMapSingle(networkDataSource::getSales)
            .toFlattenList()
            .mapWith(mapper)
            .filterWith(filter)
            .actOnSuccess(databaseDataSource::putSales)
            .map(::SalesReceived)
    }

}