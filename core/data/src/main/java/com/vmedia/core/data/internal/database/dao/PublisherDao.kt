package com.vmedia.core.data.internal.database.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.common.pure.obj.creds.RssToken
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Publisher
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface PublisherDao : BaseDao<Publisher> {

    @WorkerThread
    @Query("SELECT COUNT(*) FROM Publisher")
    fun getCount() : Long

    @Query("SELECT * FROM Publisher LIMIT 1")
    fun getPublisher(): Single<Publisher>

    @Query("SELECT * FROM Publisher LIMIT 1")
    fun getPublisherObservable(): Observable<Publisher>

    @Query("SELECT token, publisherName FROM Publisher WHERE id = :id")
    fun getRssToken(id: Long): Observable<RssToken>

}