package com.example.feature.feed.data

import com.example.feature.feed.domain.FeedRepository
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.*
import io.reactivex.Observable
import io.reactivex.Single

internal class FeedRepositoryImpl(
    private val databaseDataSource: DatabaseDataSource
) : FeedRepository {

    override fun getEvents(): Observable<List<Event>> {
        return databaseDataSource.getEvents()
    }


    override fun getEventSales(eventId: Long): Single<List<Sale>> {
        return databaseDataSource.getEventSales(eventId)
    }

    override fun getEventAssets(eventId: Long): Single<List<Asset>> {
        return databaseDataSource.getEventAssets(eventId)
    }

    override fun getEventReview(eventId: Long): Single<Review> {
        return databaseDataSource.getEventReview(eventId)
    }

    override fun getEventRevenue(eventId: Long): Single<Revenue> {
        return databaseDataSource.getEventRevenue(eventId)
    }

    override fun getEventPayout(eventId: Long): Single<Payout> {
        return databaseDataSource.getEventPayout(eventId)
    }


    override fun getAsset(id: Long): Single<Asset> {
        return databaseDataSource.getAsset(id) // todo cache
    }

    override fun getUser(id: Long): Single<User> {
        return databaseDataSource.getUser(id)
    }

}