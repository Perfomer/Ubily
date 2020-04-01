package com.vmedia.core.sync.synchronizer.revenue

import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.filterItemsAreInstance
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Revenue
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.network.entity.internal.RevenueEventDto
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
        return networkDataSource.getRevenue()
            .filterItemsAreInstance(RevenueEventDto.Revenue::class)
            .filterWith(filter)
            .mapWith(mapper)
            .actOnSuccess(databaseDataSource::putRevenues)
    }

}