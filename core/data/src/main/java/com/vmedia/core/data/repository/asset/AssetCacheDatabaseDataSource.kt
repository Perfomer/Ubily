package com.vmedia.core.data.repository.asset

import com.vmedia.core.common.android.util.CacheHolder
import com.vmedia.core.data.datasource.DatabaseDataSource

internal class AssetCacheDatabaseDataSource(
    private val source: DatabaseDataSource
): CacheHolder(), DatabaseDataSource by source {

    private val categories by cachedMapSingle(source::getCategory)

    override fun getCategory(id: Long) = categories[id]

}