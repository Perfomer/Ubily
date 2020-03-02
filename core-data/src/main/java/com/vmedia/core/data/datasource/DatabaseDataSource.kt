package com.vmedia.core.data.datasource

import com.vmedia.core.data.internal.database.dao.PublisherDao
import com.vmedia.core.data.internal.database.entity.Publisher
import io.reactivex.Observable

class DatabaseDataSource(
    private val publisherDao: PublisherDao
) {

    fun getPublisher(): Observable<Publisher> {
        return publisherDao.getPublishers().map { it[0] }
    }

}