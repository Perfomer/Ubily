package com.vmedia.core.sync.synchronizer.payout

import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.filterItemsAreInstance
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.network.entity.internal.RevenueEventDto.Payout
import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync.SynchronizationEvent.PayoutsReceived
import com.vmedia.core.sync._PayoutFilter
import com.vmedia.core.sync._PayoutMapper
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single

class PayoutSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val mapper: _PayoutMapper,
    private val filter: _PayoutFilter
) : Synchronizer<PayoutsReceived> {

    override val dataType = SynchronizationDataType.PAYOUTS

    override fun execute(): Single<PayoutsReceived> {
        return networkDataSource.getRevenue()
            .filterItemsAreInstance(Payout::class)
            .filterWith(filter)
            .mapWith(mapper)
            .actOnSuccess(databaseDataSource::putPayouts)
            .map(::PayoutsReceived)
    }

}