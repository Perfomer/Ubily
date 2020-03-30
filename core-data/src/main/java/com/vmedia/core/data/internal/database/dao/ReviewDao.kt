package com.vmedia.core.data.internal.database.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Review
import io.reactivex.Single

@Dao
interface ReviewDao : BaseDao<Review> {

    @Query("SELECT * FROM Review WHERE authorId = :authorId AND assetId = :assetId LIMIT 1")
    fun getReview(authorId: Long, assetId: Long): Single<Review>

    @WorkerThread
    @Query("SELECT COUNT(*) FROM Review WHERE authorId = :authorId AND assetId = :assetId")
    fun contains(authorId: Long, assetId: Long): Long

}