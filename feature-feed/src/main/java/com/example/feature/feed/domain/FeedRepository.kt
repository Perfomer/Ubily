package com.example.feature.feed.domain

import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.internal.database.entity.Sale
import io.reactivex.Observable
import io.reactivex.Single

internal interface FeedRepository {

    fun getEvents(): Observable<List<Event>>


    fun getEventSales(eventId: Long): Single<List<Sale>>

    fun getEventAssets(eventId: Long): Single<List<Asset>>


    fun getAsset(id: Long): Single<Asset>

}