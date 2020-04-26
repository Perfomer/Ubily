package com.vmedia.core.data.internal.database.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Keyword
import io.reactivex.Observable

@Dao
interface KeywordDao : BaseDao<Keyword> {

    @WorkerThread
    @Query("SELECT id FROM Keyword WHERE value = :keyword")
    fun getId(keyword: String): Long

    @Query(
        """
            SELECT * FROM AssetKeyword assetKeyword
                LEFT JOIN Keyword keyword ON (keyword.id = assetKeyword.keywordId)
            WHERE assetId = :assetId
            ORDER BY value
        """
    )
    fun getKeywords(assetId: Long): Observable<List<Keyword>>

}