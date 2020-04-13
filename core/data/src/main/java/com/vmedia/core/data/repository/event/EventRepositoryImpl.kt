package com.vmedia.core.data.repository.event

import com.vmedia.core.common.obj.event.EventInfo
import com.vmedia.core.common.util.flatMapWith
import com.vmedia.core.data._EventListMapper
import com.vmedia.core.data._EventMapper
import com.vmedia.core.domain.repository.EventRepository
import io.reactivex.Observable

internal class EventRepositoryImpl(
    private val source: EventCacheDatabaseDataSource,
    private val eventListMapper: _EventListMapper,
    private val eventMapper: _EventMapper
) : EventRepository {

    override fun getEvents(): Observable<List<EventInfo<*>>> {
        return source.getEvents()
            .flatMapWith(eventListMapper)
            .doOnSubscribe { source.dropCache() }
    }

    override fun getEvent(id: Long): Observable<EventInfo<*>> {
        return source.getEvent(id)
            .flatMapWith(eventMapper)
    }

}