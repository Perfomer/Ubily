package com.vmedia.core.data.internal.database.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Review
import com.vmedia.core.data.internal.database.model.ReviewDetailed
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ReviewDao : BaseDao<Review> {

    @Query("SELECT * FROM Review WHERE authorId = :authorId AND assetId = :assetId LIMIT 1")
    fun getReview(authorId: Long, assetId: Long): Single<Review>

    @Query(
        """
            SELECT * FROM Review review
                JOIN User user ON (review.authorId = user.id)
            WHERE assetId = :assetId
            ORDER BY publishingDate
        """
    )
    fun getDetailedReviews(assetId: Long): Observable<List<ReviewDetailed>>

    @Query("SELECT id FROM Review WHERE authorId = :authorId AND assetId = :assetId LIMIT 1")
    fun getReviewId(authorId: Long, assetId: Long): Single<Long>

    @Query(
        """
            SELECT * FROM EventEntity eventEntity
                LEFT JOIN Review review ON (review.id = eventEntity.entityId)
            WHERE eventEntity.eventId = :eventId
        """
    )
    fun getEventReview(eventId: Long): Single<Review>

    @Query("SELECT COUNT(*) FROM Review WHERE assetId = :assetId")
    fun getCount(assetId: Long): Observable<Int>

    @WorkerThread
    @Query("SELECT COUNT(*) FROM Review WHERE authorId = :authorId AND assetId = :assetId")
    fun contains(authorId: Long, assetId: Long): Long

}