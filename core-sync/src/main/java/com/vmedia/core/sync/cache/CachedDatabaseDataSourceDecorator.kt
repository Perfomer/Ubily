package com.vmedia.core.sync.cache

import com.vmedia.core.common.obj.Period
import com.vmedia.core.data.datasource.DatabaseDataSource
import java.math.BigDecimal

internal class CachedDatabaseDataSourceDecorator(
    private val source: DatabaseDataSource
) : CachedDataSource(), DatabaseDataSource by source {

    private val lastPayoutCache by cachedSingle(source.getLastPayout())
    private val lastRevenueCache by cachedSingle(source.getLastRevenue())
    private val lastPeriodCache by cachedSingle(source.getLastPeriod())

    private val assetsWithIds by cachedMapSingle(source::getAsset)
    private val assetsWithUrls by cachedMapSingle(source::getAssetByUrl)
    private val assetsWithNames by cachedMapSingle(source::getAssetByName)
    private val usersWithNames by cachedMapSingle(source::getUserByName)

    private val lastSales by cachedMapSingle { arguments: Triple<Long, Period, BigDecimal> ->
        source.getLastSale(
            assetId = arguments.first,
            period = arguments.second,
            priceUsd = arguments.third
        )
    }

    private val reviews by cachedMapSingle { arguments: Pair<Long, Long> ->
        source.getReview(
            authorId = arguments.first,
            assetId = arguments.second
        )
    }


    override fun getLastPayout() = lastPayoutCache
    override fun getLastRevenue() = lastRevenueCache
    override fun getLastPeriod() = lastPeriodCache

    override fun getAsset(id: Long) = assetsWithIds[id]
    override fun getAssetByUrl(url: String) = assetsWithUrls[url]
    override fun getAssetByName(name: String) = assetsWithNames[name]
    override fun getUserByName(name: String) = usersWithNames[name]

    override fun getLastSale(
        assetId: Long,
        period: Period,
        priceUsd: BigDecimal
    ) = lastSales[Triple(assetId, period, priceUsd)]

    override fun getReview(
        authorId: Long,
        assetId: Long
    ) = reviews[Pair(authorId, assetId)]

}