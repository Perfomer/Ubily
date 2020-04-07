package com.example.feature.feed.domain.mapper

import com.vmedia.core.common.obj.EventType.*
import com.vmedia.core.common.util.ObservableListMapper
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.obj.EventInfo
import io.reactivex.Observable

internal class EventMapper(
    private val saleMapper: SaleMapper
) : ObservableListMapper<Event, EventInfo<*>> {

    override fun map(from: List<Event>): Observable<List<EventInfo<*>>> {
        return Observable.fromIterable(from)
            .flatMap(::map)
            .toList()
            .toObservable()
    }

    private fun map(event: Event): Observable<EventInfo<*>> {
        return when(event.type) {
            SALE -> saleMapper.map(event) as Observable<EventInfo<*>>
            FREE_DOWNLOAD -> TODO()
            REVIEW -> TODO()
            ASSET -> TODO()
            PAYOUT -> TODO()
            REVENUE -> TODO()
            ANNIVERSARY_SALE -> TODO()
            INITIALIZATION -> TODO()
        }
    }

}