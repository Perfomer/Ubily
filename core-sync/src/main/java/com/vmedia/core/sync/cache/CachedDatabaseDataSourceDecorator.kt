package com.vmedia.core.sync.cache

import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.User
import io.reactivex.Single

internal class CachedDatabaseDataSourceDecorator(
    private val source: DatabaseDataSource
) : DatabaseDataSource by source, CacheDataSource {

    private val assetsWithIds: MutableMap<Long, Asset> = mutableMapOf()
    private val assetsWithUrls: MutableMap<String, Asset> = mutableMapOf()
    private val assetsWithNames: MutableMap<String, Asset> = mutableMapOf()
    private val usersWithNames: MutableMap<String, User> = mutableMapOf()

    override fun dropCache() {
        assetsWithIds.clear()
        assetsWithUrls.clear()
        assetsWithNames.clear()
        usersWithNames.clear()
    }

    override fun getAsset(id: Long): Single<Asset> {
        return extractWithCache(assetsWithIds[id], source.getAsset(id)) {
            assetsWithIds[id] = it
        }
    }

    override fun getAssetByUrl(url: String): Single<Asset> {
        return extractWithCache(assetsWithUrls[url], source.getAssetByUrl(url)) {
            assetsWithUrls[url] = it
        }
    }

    override fun getAssetByName(name: String): Single<Asset> {
        return extractWithCache(assetsWithNames[name], source.getAssetByName(name)) {
            assetsWithNames[name] = it
        }
    }

    override fun getUserByName(name: String): Single<User> {
        return extractWithCache(usersWithNames[name], source.getUserByName(name)) {
            usersWithNames[name] = it
        }
    }

}