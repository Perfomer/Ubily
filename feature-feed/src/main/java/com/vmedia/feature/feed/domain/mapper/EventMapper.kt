package com.vmedia.feature.feed.domain.mapper

import com.vmedia.core.common.obj.EventType.*
import com.vmedia.core.common.util.ObservableListMapper
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.obj.EventInfo
import com.vmedia.feature.feed.*
import io.reactivex.Observable

internal class EventMapper(
    private val saleMapper: _SaleMapper,
    private val downloadMapper: _DownloadMapper,
    private val assetMapper: _AssetMapper,
    private val reviewMapper: _ReviewMapper,
    private val revenueMapper: _RevenueMapper,
    private val payoutMapper: _PayoutMapper
) : ObservableListMapper<Event, EventInfo<*>> {

    override fun map(from: List<Event>): Observable<List<EventInfo<*>>> {
        return Observable.defer {
            Observable.fromIterable(from)
                .flatMap(::map)
                .toList()
                .toObservable()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun map(event: Event): Observable<EventInfo<*>> {
        val mapper = when (event.type) {
            SALE -> saleMapper
            FREE_DOWNLOAD -> downloadMapper
            REVIEW -> reviewMapper
            ASSET -> assetMapper
            PAYOUT -> payoutMapper
            REVENUE -> revenueMapper
            ANNIVERSARY_SALE -> TODO()
            INITIALIZATION -> TODO()
        }

        return mapper.map(event) as Observable<EventInfo<*>>
    }

}