package com.vmedia.core.data.internal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Asset
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface AssetDao : BaseDao<Asset> {

    @Query("SELECT * FROM Asset")
    fun getAssets(): Observable<List<Asset>>

    @Query("SELECT * FROM Asset WHERE id = :id")
    fun getAsset(id: Long): Single<Asset>

    @Query("SELECT * FROM Asset WHERE name = :name")
    fun getAssetByName(name: String): Single<Asset>

    @Query("SELECT * FROM Asset WHERE shortUrl = :url")
    fun getAssetByUrl(url: String): Single<Asset>

    @Query(
        """
            SELECT * FROM Asset
            WHERE 
                priceUsd = 0
                AND publishingDate != 0
            ORDER BY publishingDate DESC
            LIMIT 1
        """
    )
    fun getFirstFreeAsset(): Single<Asset>
}