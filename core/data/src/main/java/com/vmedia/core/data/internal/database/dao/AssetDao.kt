package com.vmedia.core.data.internal.database.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Asset
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface AssetDao : BaseDao<Asset> {

    @Query(
        """
            SELECT * FROM Asset
            ORDER BY 
                CASE WHEN status = "DRAFT"
                    THEN 0
                    ELSE 1
                END DESC,
                publishingDate DESC,
                name,
                id
        """
    )
    fun getAssets(): Observable<List<Asset>>

    @Query("SELECT * FROM Asset WHERE id = :id")
    fun getAsset(id: Long): Single<Asset>

    @Query("SELECT * FROM Asset WHERE id = :id")
    fun getAssetObservable(id: Long): Observable<Asset>

    @Query("SELECT * FROM Asset WHERE shortUrl = :url")
    fun getAssetByUrl(url: String): Single<Asset>

    @Query("SELECT AVG(averageRating) FROM Asset WHERE averageRating > 0")
    fun getAverageAssetsRating(): Observable<Double>

    @Query(
        """
            SELECT * FROM Asset
            WHERE 
                priceUsd = "0.00"
                AND publishingDate != 0
            ORDER BY publishingDate DESC
            LIMIT 1
        """
    )
    fun getFirstFreeAsset(): Single<Asset>

    @Query(
        """
            SELECT * FROM EventEntity eventEntity
                LEFT JOIN Asset asset ON (asset.id = eventEntity.entityId)
            WHERE eventEntity.eventId = :eventId
        """
    )
    fun getEventAssets(eventId: Long): Single<List<Asset>>

    @WorkerThread
    @Query("SELECT COUNT(*) FROM Asset WHERE id = :id")
    fun contains(id: Long): Long
}