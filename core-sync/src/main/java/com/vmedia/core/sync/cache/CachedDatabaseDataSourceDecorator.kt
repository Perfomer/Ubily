package com.vmedia.core.sync.cache

import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Asset
import io.reactivex.Observable

internal class CachedDatabaseDataSourceDecorator(
    private val source: DatabaseDataSource
) : DatabaseDataSource by source, CacheDataSource {

    private val assets: MutableMap<Long, Asset> = mutableMapOf()

    override fun dropCache() {
        assets.clear()
    }

    override fun getAsset(id: Long): Observable<Asset> {
        return extractWithCache(assets[id], source.getAsset(id))
    }

}