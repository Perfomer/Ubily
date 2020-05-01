package com.vmedia.core.data.internal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.common.pure.obj.Month
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.PeriodWrap
import io.reactivex.Single

@Dao
internal interface PeriodDao : BaseDao<PeriodWrap> {

    @Query("SELECT * FROM Period WHERE id = :id")
    fun getPeriod(id: Long): Single<PeriodWrap>

    @Query(
        """
            SELECT * FROM Period
            ORDER BY 
                year DESC, 
                month DESC 
            LIMIT 1
        """
    )
    fun getLastPeriod(): Single<PeriodWrap>

    @Query(
        """
            SELECT * FROM Period
            WHERE 
                year > :year
                OR (year = :year AND month >= :month)
        """
    )
    fun getPeriodsAfter(year: Int, month: Month): Single<List<PeriodWrap>>

    @Query(
        """
            SELECT id FROM Period
            WHERE 
                year = :year
                AND month = :month
        """
    )
    fun getPeriodId(year: Int, month: Month): Single<Long>

}