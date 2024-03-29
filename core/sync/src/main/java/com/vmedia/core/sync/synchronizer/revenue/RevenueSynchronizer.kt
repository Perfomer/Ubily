package com.vmedia.core.sync.synchronizer.revenue

import com.vmedia.core.common.pure.util.rx.actOnSuccess
import com.vmedia.core.common.pure.util.rx.filterItemsAreInstance
import com.vmedia.core.common.pure.util.filterWith
import com.vmedia.core.common.pure.util.mapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Revenue
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.network.entity.internal.IncomeDto
import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync._RevenueFilter
import com.vmedia.core.sync._RevenueMapper
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single

class RevenueSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val mapper: _RevenueMapper,
    private val filter: _RevenueFilter
) : Synchronizer<List<Revenue>> {

    override val dataType = SynchronizationDataType.REVENUES

    override fun execute(): Single<List<Revenue>> {
        return networkDataSource.getIncome()
            .filterItemsAreInstance(IncomeDto.Revenue::class)
            .filterWith(filter)
            .mapWith(mapper)
            .actOnSuccess(databaseDataSource::putRevenues)
    }

}
