package com.vmedia.core.data.datasource

import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.Period
import com.vmedia.core.data.internal.database.entity.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.math.BigDecimal
import java.util.*

interface DatabaseDataSource {

    fun getPublisher(): Single<Publisher>

    fun getAsset(id: Long): Single<Asset>

    fun getAssetByUrl(url: String): Single<Asset>

    fun getUserByName(name: String): Single<User>

    fun getReview(authorId: Long, assetId: Long): Single<Review>

    fun getReviewId(authorId: Long, assetId: Long): Single<Long>

    fun getLastPayout(): Single<Payout>

    fun getLastRevenue(): Single<Revenue>

    fun getLastSale(assetId: Long, period: Period, priceUsd: BigDecimal): Single<Sale>

    fun getSaleId(assetId: Long, date: Date, priceUsd: BigDecimal): Single<Long>

    fun getFreeDownloadsPeriods(): Single<List<Period>>

    fun getPeriodId(period: Period): Single<Long>

    fun getLastPeriod(): Single<Period>

    fun getEvents(): Observable<List<Event>>

    fun hasEvents(): Single<Boolean>


    fun getEventSales(eventId: Long) : Single<List<Sale>>

    fun getEventAssets(eventId: Long) : Single<List<Asset>>


    fun putEvent(type: EventType, date: Date, entityIds: Collection<Long>): Completable

    fun putPublisher(publisher: Publisher): Completable

    fun putAsset(
        asset: Asset,
        images: Collection<AssetImage>,
        keywords: Collection<String>
    ): Completable

    fun putSales(sales: Collection<Sale>): Completable

    fun putRevenues(revenues: Collection<Revenue>): Completable

    fun putPayouts(payouts: Collection<Payout>): Completable

    fun putPeriods(periods: Collection<Period>): Completable

    fun putReviews(reviews: Collection<Review>): Completable

    fun putUsers(users: Collection<User>): Completable

}