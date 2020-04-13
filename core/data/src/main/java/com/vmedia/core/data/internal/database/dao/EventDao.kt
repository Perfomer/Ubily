package com.vmedia.core.data.internal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Event
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface EventDao: BaseDao<Event> {

    @Query("SELECT * FROM Event ORDER BY date DESC")
    fun getEvents(): Observable<List<Event>>

    @Query("SELECT * FROM Event WHERE id = :id")
    fun getEvent(id: Long): Observable<Event>

    @Query("SELECT COUNT(*) FROM Event")
    fun getEventsCount(): Single<Long>

}