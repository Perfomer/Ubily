package com.vmedia.feature.feed.domain

import com.vmedia.core.common.util.flatMapWith
import com.vmedia.core.data.obj.EventInfo
import com.vmedia.feature.feed.domain.di._EventMapper
import io.reactivex.Observable

class FeedInteractor(
    private val repository: FeedRepository,
    private val eventMapper: _EventMapper
) {

    fun getEvents(): Observable<List<EventInfo<*>>> {
        return repository.getEvents()
            .flatMapWith(eventMapper)
    }

}