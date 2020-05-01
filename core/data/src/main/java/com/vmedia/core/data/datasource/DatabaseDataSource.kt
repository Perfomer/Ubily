package com.vmedia.core.data.datasource

import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.Period
import com.vmedia.core.data.internal.database.entity.*
import com.vmedia.core.data.internal.database.model.ReviewDetailed
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.math.BigDecimal
import java.util.*

interface DatabaseDataSource {

    fun getPublisher(): Single<Publisher>

    fun getPublisherObservable(): Observable<Publisher>

    fun getCategory(id: Long): Single<Category>

    fun getAssetObservable(id: Long): Observable<Asset>

    fun getAsset(id: Long): Single<Asset>

    fun getAssetByUrl(url: String): Single<Asset>

    fun getAssets(): Observable<List<Asset>>

    fun getAverageAssetsRating(): Observable<Double>

    fun getArtworks(assetId: Long): Observable<List<String>>

    fun getUser(id: Long): Single<User>

    fun getUserByName(name: String): Single<User>

    fun getReviewsCount(assetId: Long): Observable<Int>

    fun getDetailedReviews(assetId: Long): Observable<List<ReviewDetailed>>

    fun getReview(authorId: Long, assetId: Long): Single<Review>

    fun getReviewId(authorId: Long, assetId: Long): Single<Long>

    fun getLastPayout(): Single<Payout>

    fun getRevenue(id: Long): Single<Revenue>

    fun getLastRevenue(): Single<Revenue>

    fun getLastSale(assetId: Long, period: Period, priceUsd: BigDecimal): Single<Sale>

    fun getSaleId(assetId: Long, date: Date, priceUsd: BigDecimal): Single<Long>

    fun getFreeDownloadsPeriods(): Single<List<Period>>

    fun getKeywords(assetId: Long): Observable<List<Keyword>>

    fun getPeriod(id: Long): Single<Period>

    fun getPeriodId(period: Period): Single<Long>

    fun getLastPeriod(): Single<Period>

    fun getEvents(): Observable<List<Event>>

    fun getEvent(id: Long): Observable<Event>

    fun hasEvents(): Single<Boolean>


    fun getEventSales(eventId: Long): Single<List<Sale>>

    fun getEventAssets(eventId: Long): Single<List<Asset>>

    fun getEventReview(eventId: Long): Single<Review>

    fun getEventRevenue(eventId: Long): Single<Revenue>

    fun getEventPayout(eventId: Long): Single<Payout>


    fun putEvent(type: EventType, date: Date, entityIds: Collection<Long>): Completable

    fun putPublisher(publisher: Publisher): Completable

    fun putAsset(
        asset: Asset,
        images: Collection<Artwork>,
        keywords: Collection<String>
    ): Completable

    fun putSales(sales: Collection<Sale>): Completable

    fun putRevenues(revenues: Collection<Revenue>): Completable

    fun putPayouts(payouts: Collection<Payout>): Completable

    fun putPeriods(periods: Collection<Period>): Completable

    fun putReviews(reviews: Collection<Review>): Completable

    fun putUsers(users: Collection<User>): Completable

    fun putCategories(categories: List<Category>): Completable

}