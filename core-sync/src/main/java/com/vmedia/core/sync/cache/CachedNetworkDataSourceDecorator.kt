package com.vmedia.core.sync.cache

import com.vmedia.core.network.datasource.NetworkDataSource

internal class CachedNetworkDataSourceDecorator(
    private val source: NetworkDataSource
) : CachedDataSource(), NetworkDataSource by source {

    private val publisherIdCache by cachedSingle(source.getPublisherId())
    private val publisherCache by cachedSingle(source.getPublisherInfo())
    private val periodsCache by cachedSingle(source.getPeriods())
    private val incomeCache by cachedSingle(source.getIncome())
    private val reviewsCache by cachedSingle(source.getReviews())

    override fun getPublisherId() = publisherIdCache
    override fun getPeriods() = periodsCache
    override fun getPublisherInfo() = publisherCache
    override fun getIncome() = incomeCache
    override fun getReviews() = reviewsCache

}