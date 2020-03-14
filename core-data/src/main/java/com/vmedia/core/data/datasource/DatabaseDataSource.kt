package com.vmedia.core.data.datasource

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

    fun putSales(sales: List<Sale>): Completable

    fun putRevenues(revenues: List<Revenue>): Completable

    fun putPayouts(payouts: List<Payout>): Completable

}