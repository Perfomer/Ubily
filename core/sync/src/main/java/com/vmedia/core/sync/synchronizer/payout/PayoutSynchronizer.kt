package com.vmedia.core.sync.synchronizer.payout

import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.filterItemsAreInstance
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Payout
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.network.entity.internal.IncomeDto
import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync._PayoutFilter
import com.vmedia.core.sync._PayoutMapper
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single

class PayoutSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val mapper: _PayoutMapper,
    private val filter: _PayoutFilter
) : Synchronizer<List<Payout>> {

    override val dataType = SynchronizationDataType.PAYOUTS

    override fun execute(): Single<List<Payout>> {
        return networkDataSource.getIncome()
            .filterItemsAreInstance(IncomeDto.Payout::class)
            .filterWith(filter)
            .mapWith(mapper)
            .actOnSuccess(databaseDataSource::putPayouts)
    }

}