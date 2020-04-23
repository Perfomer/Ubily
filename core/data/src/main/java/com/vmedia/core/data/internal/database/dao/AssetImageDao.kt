package com.vmedia.core.data.internal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.AssetImage
import io.reactivex.Observable

@Dao
interface AssetImageDao : BaseDao<AssetImage> {

    @Query("SELECT url FROM AssetImage WHERE assetId = :assetId")
    fun getArtworks(assetId: Long): Observable<List<String>>

}