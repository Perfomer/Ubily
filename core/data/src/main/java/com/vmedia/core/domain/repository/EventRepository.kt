package com.vmedia.core.domain.repository

import com.vmedia.core.common.pure.obj.event.EventInfo
import io.reactivex.Observable

interface EventRepository {

    fun getEvents(): Observable<List<EventInfo<*>>>

    fun getEvent(id: Long): Observable<EventInfo<*>>

}