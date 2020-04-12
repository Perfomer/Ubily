package com.vmedia.core.domain.repository

import com.vmedia.core.data.obj.EventInfo
import io.reactivex.Observable

interface EventRepository {

    fun getEvents(): Observable<List<EventInfo<*>>>

}