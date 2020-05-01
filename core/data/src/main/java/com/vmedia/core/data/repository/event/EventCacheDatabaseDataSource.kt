package com.vmedia.core.data.repository.event

import com.vmedia.core.common.pure.util.CacheHolder
import com.vmedia.core.data.datasource.DatabaseDataSource

internal class EventCacheDatabaseDataSource(
    private val source: DatabaseDataSource
): CacheHolder(), DatabaseDataSource by source {

    private val assets by cachedMapSingle(source::getAsset)
    private val users by cachedMapSingle(source::getUser)
    private val periods by cachedMapSingle(source::getPeriod)
    private val revenues by cachedMapSingle(source::getRevenue)

    override fun getAsset(id: Long) = assets[id]
    override fun getUser(id: Long) = users[id]
    override fun getPeriod(id: Long) = periods[id]
    override fun getRevenue(id: Long) = revenues[id]

}