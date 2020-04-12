package com.vmedia.core.data.internal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Payout
import io.reactivex.Single

@Dao
interface PayoutDao : BaseDao<Payout> {

    @Query("SELECT * FROM Payout WHERE date = (SELECT MAX(date) FROM Payout)")
    fun getLastPayout(): Single<Payout>

    @Query(
        """
            SELECT * FROM EventEntity eventEntity
                LEFT JOIN Payout payout ON (payout.periodId = eventEntity.entityId)
            WHERE eventEntity.eventId = :eventId
        """
    )
    fun getEventPayout(eventId: Long): Single<Payout>

}