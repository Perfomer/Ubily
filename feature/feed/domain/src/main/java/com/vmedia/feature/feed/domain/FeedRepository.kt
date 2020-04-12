package com.vmedia.feature.feed.domain

import com.vmedia.core.common.obj.Period
import com.vmedia.core.data.internal.database.entity.*
import io.reactivex.Observable
import io.reactivex.Single

interface FeedRepository {

    fun getEvents(): Observable<List<Event>>


    fun getEventSales(eventId: Long): Single<List<Sale>>

    fun getEventAssets(eventId: Long): Single<List<Asset>>

    fun getEventReview(eventId: Long): Single<Review>

    fun getEventRevenue(eventId: Long): Single<Revenue>

    fun getEventPayout(eventId: Long): Single<Payout>


    fun getAsset(id: Long): Single<Asset>

    fun getUser(id: Long): Single<User>

    fun getPeriod(id: Long): Single<Period>

    fun getRevenue(id: Long): Single<Revenue>

}