package com.example.feature.feed.domain

import com.example.feature.feed.domain.mapper.EventMapper
import com.vmedia.core.common.util.flatMapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.obj.EventInfo
import io.reactivex.Observable

internal class FeedInteractor(
    private val dataSource: DatabaseDataSource,
    private val eventMapper: EventMapper
) {

    fun getEvents(): Observable<List<EventInfo<*>>> {
        return dataSource.getEvents()
            .flatMapWith(eventMapper)
    }

}