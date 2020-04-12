package com.vmedia.core.data.internal.database.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Keyword

@Dao
interface KeywordDao: BaseDao<Keyword> {

    @WorkerThread
    @Query("SELECT id FROM Keyword WHERE value = :keyword")
    fun getId(keyword: String): Long

}