package com.vmedia.core.sync.synchronizer.period

import com.vmedia.core.common.pure.obj.Period
import com.vmedia.core.common.pure.util.rx.actOnSuccess
import com.vmedia.core.common.pure.util.filterWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync._PeriodFilter
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single

class PeriodSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val filter: _PeriodFilter
) : Synchronizer<List<Period>> {

    override val dataType = SynchronizationDataType.PERIODS

    override fun execute(): Single<List<Period>> {
        return networkDataSource.getPeriods()
            .filterWith(filter)
            .actOnSuccess(databaseDataSource::putPeriods)
    }

}
