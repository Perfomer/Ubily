package com.vmedia.core.data.datasource

import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.AssetImage
import com.vmedia.core.data.internal.database.entity.Publisher
import com.vmedia.core.data.internal.database.entity.Sale
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

}