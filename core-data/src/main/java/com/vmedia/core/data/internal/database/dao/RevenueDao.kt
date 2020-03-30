package com.vmedia.core.data.internal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Revenue
import io.reactivex.Single

@Dao
interface RevenueDao : BaseDao<Revenue> {

    @Query("SELECT * FROM Revenue WHERE date = (SELECT MAX(date) FROM Revenue)")
    fun getLastRevenue(): Single<Revenue>

}