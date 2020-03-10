package com.vmedia.core.sync.cache

import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Asset
import io.reactivex.Observable

internal class CachedDatabaseDataSourceDecorator(
    private val source: DatabaseDataSource
) : DatabaseDataSource by source, CacheDataSource {

    private val assetsWithIds: MutableMap<Long, Asset> = mutableMapOf()

    private val assetsWithNames: MutableMap<String, Asset> = mutableMapOf()

    override fun dropCache() {
        assetsWithIds.clear()
        assetsWithNames.clear()
    }

    override fun getAsset(id: Long): Observable<Asset> {
        return extractWithCache(assetsWithIds[id], source.getAsset(id)) {
            assetsWithIds[id] = it
        }
    }

    override fun getAsset(name: String): Observable<Asset> {
        return extractWithCache(assetsWithNames[name], source.getAsset(name)) {
            assetsWithNames[name] = it
        }
    }

}