package com.vmedia.core.sync.synchronizer.revenue

import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationEvent.RevenuesReceived
import com.vmedia.core.sync.SynchronizationEventType
import com.vmedia.core.sync._RevenueFilter
import com.vmedia.core.sync._RevenueMapper
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single

class RevenueSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val mapper: _RevenueMapper,
    private val filterByInstance: _RevenueFilter,
    private val filterByDate: _RevenueFilter
) : Synchronizer<RevenuesReceived> {

    override val eventType = SynchronizationEventType.REVENUES_RECEIVED

    override fun execute(): Single<RevenuesReceived> {
        return networkDataSource.getRevenue()
            .filterWith(filterByInstance)
            .filterWith(filterByDate)
            .mapWith(mapper)
            .actOnSuccess(databaseDataSource::putRevenues)
            .map(::RevenuesReceived)
    }

}