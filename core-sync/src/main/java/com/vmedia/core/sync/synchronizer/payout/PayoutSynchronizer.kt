package com.vmedia.core.sync.synchronizer.payout

import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationEvent.PayoutsReceived
import com.vmedia.core.sync.SynchronizationEventType
import com.vmedia.core.sync._PayoutMapper
import com.vmedia.core.sync._RevenueFilter
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single

internal class PayoutSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val mapper: _PayoutMapper,
    private val filterByInstance: _RevenueFilter,
    private val filterByDate: _RevenueFilter
) : Synchronizer<PayoutsReceived> {

    override val eventType = SynchronizationEventType.PAYOUTS_RECEIVED

    override fun execute(): Single<PayoutsReceived> {
        return networkDataSource.getRevenue()
            .filterWith(filterByInstance)
            .filterWith(filterByDate)
            .mapWith(mapper)
            .actOnSuccess(databaseDataSource::putPayouts)
            .map(::PayoutsReceived)
    }

}