package com.vmedia.core.data.datasource

import com.vmedia.core.common.obj.Period
import com.vmedia.core.data.internal.database.entity.*
import io.reactivex.Completable
import io.reactivex.Single
import java.math.BigDecimal

interface DatabaseDataSource {

    fun getPublisher(): Single<Publisher>

    fun getAsset(id: Long): Single<Asset>

    fun getAssetByName(name: String): Single<Asset>

    fun getAssetByUrl(url: String): Single<Asset>

    fun getUserByName(name: String): Single<User>

    fun getReview(authorId: Long, assetId: Long): Single<Review>

    fun getLastSale(assetId: Long, period: Period, priceUsd: BigDecimal) : Single<Sale>

    fun getLastPayout() : Single<Payout>

    fun getLastRevenue() : Single<Revenue>

    fun getLastPeriod() : Single<Period>

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

    fun putReviews(reviews: List<Review>): Completable

    fun putUsers(users: List<User>): Completable

}