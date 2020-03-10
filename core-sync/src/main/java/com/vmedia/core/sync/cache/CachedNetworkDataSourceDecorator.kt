package com.vmedia.core.sync.cache

import com.vmedia.core.common.obj.Period
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.network.entity.PublisherDto
import io.reactivex.Single

internal class CachedNetworkDataSourceDecorator(
    private val source: NetworkDataSource
) : NetworkDataSource by source, CacheDataSource {

    private var publisherId: Long? = null
    private var publisher: PublisherDto? = null
    private var periods: List<Period>? = null

    override fun dropCache() {
        publisherId = null
        publisher = null
        periods = null
    }

    override fun getPublisherId(): Single<Long> {
        return extractWithCache(publisherId, source.getPublisherId()) {
            publisherId = it
        }
    }

    override fun getPeriods(): Single<List<Period>> {
        return extractWithCache(periods, source.getPeriods()) {
            periods = it
        }
    }

    override fun getPublisherInfo(): Single<PublisherDto> {
        return extractWithCache(publisher, source.getPublisherInfo()) {
            publisher = it
        }
    }

}