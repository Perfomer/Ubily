package com.vmedia.core.data.internal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Artwork
import io.reactivex.Observable

@Dao
interface ArtworkDao : BaseDao<Artwork> {

    @Query("SELECT * FROM Artwork WHERE assetId = :assetId")
    fun getArtworks(assetId: Long): Observable<List<Artwork>>

}