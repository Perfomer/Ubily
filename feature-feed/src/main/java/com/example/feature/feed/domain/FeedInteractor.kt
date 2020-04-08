package com.example.feature.feed.domain

import com.example.feature.feed._EventMapper
import com.vmedia.core.common.util.flatMapWith
import com.vmedia.core.data.obj.EventInfo
import io.reactivex.Observable

internal class FeedInteractor(
    private val repository: FeedRepository,
    private val eventMapper: _EventMapper
) {

    fun getEvents(): Observable<List<EventInfo<*>>> {
        return repository.getEvents()
            .flatMapWith(eventMapper)
    }

}