package com.vmedia.core.data.internal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Publisher
import com.vmedia.core.network.obj.RssToken
import io.reactivex.Observable

@Dao
interface PublisherDao : BaseDao<Publisher> {

    @Query("SELECT * FROM Publisher")
    fun getPublishers(): Observable<List<Publisher>>

    @Query("SELECT * FROM Publisher WHERE id = :id")
    fun getPublisher(id: Long): Observable<Publisher>

    @Query("SELECT token, publisherName FROM Publisher WHERE id = :id")
    fun getRssToken(id: Long): Observable<RssToken>

}