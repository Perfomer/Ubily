package com.vmedia.core.sync.synchronizer.period

import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationEvent.PeriodsReceived
import com.vmedia.core.sync.SynchronizationEventType
import com.vmedia.core.sync._PeriodFilter
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single

class PeriodSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val filter: _PeriodFilter
) : Synchronizer<PeriodsReceived> {

    override val eventType = SynchronizationEventType.PERIODS_RECEIVED

    override fun execute(): Single<PeriodsReceived> {
        return networkDataSource.getPeriods()
            .filterWith(filter)
            .actOnSuccess(databaseDataSource::putPeriods)
            .map(::PeriodsReceived)
    }

}