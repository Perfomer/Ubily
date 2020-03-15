package com.vmedia.core.data.datasource

import com.vmedia.core.common.obj.Period
import com.vmedia.core.data.internal.database.dao.AssetDao
import com.vmedia.core.data.internal.database.dao.PublisherDao
import com.vmedia.core.data.internal.database.entity.*
import io.reactivex.Completable
import io.reactivex.Single
import java.math.BigDecimal

class DatabaseDataSourceImpl(
    private val publisherDao: PublisherDao,
    private val assetDao: AssetDao
) : DatabaseDataSource {

    override fun getPublisher(): Single<Publisher> {
        return publisherDao.getPublisher().singleOrError()
    }

    override fun getAsset(id: Long): Single<Asset> {
        return assetDao.getAsset(id).singleOrError()
    }

    override fun putPublisher(publisher: Publisher): Completable {
        TODO("Not yet implemented")
    }

    override fun getAssetByName(name: String): Single<Asset> {
        TODO("Not yet implemented")
    }

    override fun putSales(sales: Collection<Sale>): Completable {
        TODO("Not yet implemented")
    }

    override fun putAsset(
        asset: Asset,
        images: Collection<AssetImage>,
        keywords: Collection<String>
    ): Completable {
        TODO("Not yet implemented")
    }

    override fun getAssetByUrl(url: String): Single<Asset> {
        TODO("Not yet implemented")
    }

    override fun getUserByName(name: String): Single<User> {
        TODO("Not yet implemented")
    }

    override fun getReview(authorId: Long, assetId: Long): Single<Review> {
        TODO("Not yet implemented")
    }

    override fun getLastSale(assetId: Long, period: Period, priceUsd: BigDecimal): Single<Sale> {
        TODO("Not yet implemented")
    }

    override fun getLastPayout(): Single<Payout> {
        TODO("Not yet implemented")
    }

    override fun getLastRevenue(): Single<Revenue> {
        TODO("Not yet implemented")
    }

    override fun getLastPeriod(): Single<Period> {
        TODO("Not yet implemented")
    }

    override fun putRevenues(revenues: Collection<Revenue>): Completable {
        TODO("Not yet implemented")
    }

    override fun putPayouts(payouts: Collection<Payout>): Completable {
        TODO("Not yet implemented")
    }

    override fun putPeriods(periods: Collection<Period>): Completable {
        TODO("Not yet implemented")
    }

    override fun putReviews(reviews: List<Review>): Completable {
        TODO("Not yet implemented")
    }

    override fun putUsers(users: List<User>): Completable {
        TODO("Not yet implemented")
    }

}