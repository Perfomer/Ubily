package com.example.feature.feed.data

import com.example.feature.feed.domain.FeedRepository
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.internal.database.entity.Sale
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


    override fun getAsset(id: Long): Single<Asset> {
        return databaseDataSource.getAsset(id) // todo cache
    }

}