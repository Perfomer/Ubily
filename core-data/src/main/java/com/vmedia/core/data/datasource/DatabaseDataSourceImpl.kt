package com.vmedia.core.data.datasource

import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.obj.endTimestamp
import com.vmedia.core.common.obj.startTimestamp
import com.vmedia.core.common.obj.toPeriod
import com.vmedia.core.common.util.mapItems
import com.vmedia.core.data.internal.database.UbilyDatabase
import com.vmedia.core.data.internal.database.dao.*
import com.vmedia.core.data.internal.database.entity.*
import com.vmedia.core.data.util.completableTransaction
import io.reactivex.Completable
import io.reactivex.Single
import java.math.BigDecimal
import java.util.*

internal class DatabaseDataSourceImpl(
    private val database: UbilyDatabase,

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

    override fun getAssetByName(name: String): Single<Asset> {
        return assetDao.getAssetByName(name)
    }

    override fun getAssetByUrl(url: String): Single<Asset> {
        return assetDao.getAssetByUrl(url)
    }

    override fun getReview(authorId: Long, assetId: Long): Single<Review> {
        return reviewDao.getReview(authorId, assetId)
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


    override fun putPublisher(publisher: Publisher): Completable {
        return Completable.fromAction { publisherDao.insertWithReplace(publisher) }
    }

    override fun putSales(sales: Collection<Sale>): Completable {
        return Completable.fromAction { saleDao.insertWithReplace(sales) }
    }

    override fun putAsset(
        asset: Asset,
        images: Collection<AssetImage>,
        keywords: Collection<String>
    ): Completable {
        return database.completableTransaction {
            val id = assetDao.insertWithReplace(asset)
            val keywordsIds = keywordDao.insert(keywords.map { Keyword(value = it) })
            assetKeywordDao.insert(keywordsIds.map { AssetKeyword(id, it) })
            assetImageDao.insert(images.map { it.copy(assetId = id) })
        }
    }

    override fun putRevenues(revenues: Collection<Revenue>): Completable {
        return Completable.fromAction {
            revenueDao.insert(revenues)
        }
    }

    override fun putPayouts(payouts: Collection<Payout>): Completable {
        return Completable.fromAction {
            payoutDao.insert(payouts)
        }
    }

    override fun putPeriods(periods: Collection<Period>): Completable {
        return Completable.fromAction {
            periodDao.insert(periods.map { PeriodWrap(period = it) })
        }
    }

    override fun putReviews(reviews: Collection<Review>): Completable {
        return Completable.fromAction {
            reviewDao.insert(reviews)
        }
    }

    override fun putUsers(users: Collection<User>): Completable {
        return Completable.fromAction {
            userDao.insert(users)
        }
    }

}