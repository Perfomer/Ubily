package com.vmedia.core.data.datasource

import com.vmedia.core.common.obj.Period
import com.vmedia.core.data.internal.database.entity.*
import io.reactivex.Completable
import io.reactivex.Observable

interface DatabaseDataSource {

    fun getPublisher(): Observable<Publisher>

    fun getAsset(id: Long): Observable<Asset>

    fun getAsset(name: String): Observable<Asset>

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