package com.vmedia.core.data.internal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.common.obj.creds.RssToken
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Publisher
import io.reactivex.Observable

@Dao
interface PublisherDao : BaseDao<Publisher> {

    @Query("SELECT * FROM Publisher LIMIT 1")
    fun getPublisher(): Observable<Publisher>

    @Query("SELECT token, publisherName FROM Publisher WHERE id = :id")
    fun getRssToken(id: Long): Observable<RssToken>

}