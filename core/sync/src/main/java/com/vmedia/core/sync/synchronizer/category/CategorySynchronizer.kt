package com.vmedia.core.sync.synchronizer.category

import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Category
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync._CategoryMapper
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single

internal class CategorySynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val mapper: _CategoryMapper
): Synchronizer<List<Category>> {

    override val dataType = SynchronizationDataType.ASSETS_CATEGORIES

    override fun execute(): Single<List<Category>> {
        return networkDataSource.getCategories()
            .mapWith(mapper)
            .actOnSuccess(databaseDataSource::putCategories)
    }

}