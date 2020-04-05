package com.vmedia.core.data.internal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Event
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface EventDao: BaseDao<Event> {

    @Query("SELECT * FROM Event")
    fun getEvents(): Observable<List<Event>>

    @Query("SELECT COUNT(*) FROM Event")
    fun getEventsCount(): Single<Long>

}