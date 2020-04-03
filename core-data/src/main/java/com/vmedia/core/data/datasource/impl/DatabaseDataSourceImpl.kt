package com.vmedia.core.data.datasource.impl

import android.util.Log
import androidx.annotation.WorkerThread
import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.obj.endTimestamp
import com.vmedia.core.common.obj.startTimestamp
import com.vmedia.core.common.obj.toPeriod
import com.vmedia.core.common.util.mapItems
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.UbilyDatabase
import com.vmedia.core.data.internal.database.dao.*
import com.vmedia.core.data.internal.database.entity.*
import com.vmedia.core.data.util.completableTransaction
import com.vmedia.core.data.util.upsert
import io.reactivex.Completable
import io.reactivex.Single
import java.math.BigDecimal
import java.util.*

internal class DatabaseDataSourceImpl(
    private val database: UbilyDatabase,

    private val eventDao: EventDao,
    private val eventEntityDao: EventEntityDao,
    private val publisherDao: PublisherDao,
    private val userDao: UserDao,
    private val saleDao: SaleDao,
    private val periodDao: PeriodDao,
    private val revenueDao: RevenueDao,
    private val payoutDao: PayoutDao,
    private val reviewDao: ReviewDao,
    private val assetDao: AssetDao,
    private val assetImageDao: AssetImageDao,
    private val keywordDao: KeywordDao,
    private val assetKeywordDao: AssetKeywordDao
) : DatabaseDataSource {

    override fun getPublisher(): Single<Publisher> {
        return publisherDao.getPublisher()
    }

    override fun getUserByName(name: String): Single<User> {
        return userDao.getUserByName(name)
    }

    override fun getAsset(id: Long): Single<Asset> {
        return assetDao.getAsset(id)
    }

    override fun getAssetByUrl(url: String): Single<Asset> {
        return assetDao.getAssetByUrl(url)
    }

    override fun getReview(authorId: Long, assetId: Long): Single<Review> {
        return reviewDao.getReview(authorId, assetId)
    }

    override fun getReviewId(authorId: Long, assetId: Long): Single<Long> {
        return reviewDao.getReviewId(authorId, assetId)
    }

    override fun getLastPayout(): Single<Payout> {
        return payoutDao.getLastPayout()
    }

    override fun getLastRevenue(): Single<Revenue> {
        return revenueDao.getLastRevenue()
    }

    override fun getLastSale(assetId: Long, period: Period, priceUsd: BigDecimal): Single<Sale> {
        return saleDao.getLastSale(assetId, priceUsd, period.startTimestamp, period.endTimestamp)
    }

    override fun getSaleId(assetId: Long, date: Date, priceUsd: BigDecimal): Single<Long> {
        return Single.fromCallable { saleDao.getSaleId(assetId, priceUsd, date) }
    }

    override fun getFreeDownloadsPeriods(): Single<List<Period>> {
        return assetDao.getFirstFreeAsset()
            .map(Asset::publishingDate)
            .map(Date::toPeriod)
            .flatMap { periodDao.getPeriodsAfter(it.year, it.month) }
            .mapItems(PeriodWrap::period)
    }

    override fun getLastPeriod(): Single<Period> {
        return periodDao.getLastPeriod().map(PeriodWrap::period)
    }

    override fun getPeriodId(period: Period): Single<Long> {
        return periodDao.getPeriodId(period.year, period.month)
    }

    override fun hasEvents(): Single<Boolean> {
        return eventDao.getEventsCount().map { it > 0 }
    }

    override fun putEvent(type: EventType, date: Date, entityIds: Collection<Long>): Completable {
        return database.completableTransaction {
            val eventId = eventDao.insert(Event(type = type, date = date))
            Log.d(DatabaseDataSource::class.simpleName, "event saved: $eventId $type $entityIds")
            eventEntityDao.insert(entityIds.map { EventEntity(eventId, it) })
        }
    }

    override fun putPublisher(publisher: Publisher): Completable {
        return database.completableTransaction {
            val contains = publisherDao.getCount() == 0L
            publisherDao.upsert(contains, publisher)
        }
    }

    override fun putSales(sales: Collection<Sale>): Completable {
        return database.completableTransaction {
            sales.forEach(::putSale)
        }
    }

    override fun putAsset(
        asset: Asset,
        images: Collection<AssetImage>,
        keywords: Collection<String>
    ): Completable {
        return database.completableTransaction {
            putAsset(asset)
            assetImageDao.insert(images)
            keywords.forEach { putKeyword(it, asset.id) }
        }
    }

    override fun putRevenues(revenues: Collection<Revenue>): Completable {
        return Completable.fromAction {
            revenueDao.insert(revenues) //todo think
        }
    }

    override fun putPayouts(payouts: Collection<Payout>): Completable {
        return Completable.fromAction {
            payoutDao.insert(payouts) //todo think
        }
    }

    override fun putPeriods(periods: Collection<Period>): Completable {
        return Completable.fromAction {
            periodDao.insert(periods.map(Period::wrap))
        }
    }

    override fun putReviews(reviews: Collection<Review>): Completable {
        return database.completableTransaction {
            reviews.forEach(::putReview)
        }
    }

    override fun putUsers(users: Collection<User>): Completable {
        return Completable.fromAction {
            userDao.insert(users)
        }
    }


    @WorkerThread
    private fun putAsset(asset: Asset) {
        val contains = assetDao.contains(asset.id) == 1L
        assetDao.upsert(contains, asset)
    }

    @WorkerThread
    private fun putKeyword(keyword: String, assetId: Long) {
        var keywordId = keywordDao.getId(keyword)

        if (keywordId == 0L) {
            keywordId = keywordDao.insert(Keyword(value = keyword))
        }

        putAssetKeyword(keywordId, assetId)
    }

    @WorkerThread
    private fun putAssetKeyword(keywordId: Long, assetId: Long) {
        val contains = assetKeywordDao.contains(keywordId, assetId) == 1L
        if (contains) return

        assetKeywordDao.insert(
            AssetKeyword(
                assetId = assetId,
                keywordId = keywordId
            )
        )
    }

    @WorkerThread
    private fun putReview(review: Review) {
        val contains = reviewDao.contains(review.authorId, review.assetId) == 1L
        reviewDao.upsert(contains, review)
    }

    @WorkerThread
    private fun putSale(sale: Sale) {
        val saleId = saleDao.getSaleId(sale.assetId, sale.priceUsd, sale.date)
        val contains = saleId != 0L
        val saleActual = if (!contains) sale else sale.copy(id = saleId)

        saleDao.upsert(contains, saleActual)
    }

}