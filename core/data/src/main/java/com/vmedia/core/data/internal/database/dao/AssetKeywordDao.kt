package com.vmedia.core.data.internal.database.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.AssetKeyword

@Dao
interface AssetKeywordDao : BaseDao<AssetKeyword> {

    @WorkerThread
    @Query(
        """
            SELECT COUNT(*) 
            FROM AssetKeyword 
            WHERE 
                keywordId = :keywordId
                AND assetId = :assetId
        """
    )
    fun contains(keywordId: Long, assetId: Long): Long

}