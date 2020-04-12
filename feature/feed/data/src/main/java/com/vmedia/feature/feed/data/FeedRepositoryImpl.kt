package com.vmedia.feature.feed.data

import com.vmedia.core.common.util.CacheHolder
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.feature.feed.domain.FeedRepository
import io.reactivex.Observable

internal class FeedRepositoryImpl(
    private val source: DatabaseDataSource
) : CacheHolder(), FeedRepository {

    private val assets by cachedMapSingle(source::getAsset)
    private val users by cachedMapSingle(source::getUser)
    private val periods by cachedMapSingle(source::getPeriod)
    private val revenues by cachedMapSingle(source::getRevenue)

    override fun getEvents(): Observable<List<Event>> {
        return source.getEvents()
            .doOnSubscribe { dropCache() }
    }

    override fun getEventSales(eventId: Long) = source.getEventSales(eventId)
    override fun getEventAssets(eventId: Long) = source.getEventAssets(eventId)
    override fun getEventReview(eventId: Long) = source.getEventReview(eventId)
    override fun getEventRevenue(eventId: Long) = source.getEventRevenue(eventId)
    override fun getEventPayout(eventId: Long) = source.getEventPayout(eventId)

    override fun getAsset(id: Long) = assets[id]
    override fun getUser(id: Long) = users[id]
    override fun getPeriod(id: Long) = periods[id]
    override fun getRevenue(id: Long) = revenues[id]

}