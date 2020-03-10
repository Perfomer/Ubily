package com.vmedia.core.data.datasource

import com.vmedia.core.data.internal.database.dao.AssetDao
import com.vmedia.core.data.internal.database.dao.PublisherDao
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.AssetImage
import com.vmedia.core.data.internal.database.entity.Publisher
import io.reactivex.Completable
import io.reactivex.Observable

class DatabaseDataSourceImpl(
    private val publisherDao: PublisherDao,
    private val assetDao: AssetDao
) : DatabaseDataSource {

    override fun getPublisher(): Observable<Publisher> {
        return publisherDao.getPublishers().map { it[0] }
    }

    override fun getAsset(id: Long): Observable<Asset> {
        return assetDao.getAsset(id)
    }

    override fun putPublisher(publisher: Publisher): Completable {
        TODO("Not yet implemented")
    }

    override fun putAsset(
        asset: Asset,
        images: Collection<AssetImage>,
        keywords: Collection<String>
    ): Completable {
        TODO("Not yet implemented")
    }
    
}