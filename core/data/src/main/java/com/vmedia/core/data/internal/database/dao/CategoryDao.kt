package com.vmedia.core.data.internal.database.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Category
import io.reactivex.Single

@Dao
interface CategoryDao : BaseDao<Category> {

    @Query("SELECT * FROM Category WHERE id = :id")
    fun getCategory(id: Long): Single<Category>

    @WorkerThread
    @Query("SELECT COUNT(*) FROM Category WHERE id = :id")
    fun contains(id: Long): Int

}