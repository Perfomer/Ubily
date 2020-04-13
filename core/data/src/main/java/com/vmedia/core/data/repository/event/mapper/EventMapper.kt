package com.vmedia.core.data.repository.event.mapper

import com.vmedia.core.common.obj.EventType.*
import com.vmedia.core.common.obj.event.EventInfo
import com.vmedia.core.common.util.ObservableMapper
import com.vmedia.core.data.*
import com.vmedia.core.data.internal.database.entity.Event
import io.reactivex.Observable

internal class EventMapper(
    private val saleMapper: _SaleMapper,
    private val downloadMapper: _DownloadMapper,
    private val assetMapper: _AssetMapper,
    private val reviewMapper: _ReviewMapper,
    private val revenueMapper: _RevenueMapper,
    private val payoutMapper: _PayoutMapper
) : ObservableMapper<Event, EventInfo<*>> {

    @Suppress("UNCHECKED_CAST")
    override fun map(from: Event): Observable<EventInfo<*>> {
        val mapper = when (from.type) {
            SALE -> saleMapper
            FREE_DOWNLOAD -> downloadMapper
            REVIEW -> reviewMapper
            ASSET -> assetMapper
            PAYOUT -> payoutMapper
            REVENUE -> revenueMapper
            ANNIVERSARY_SALE -> TODO()
            INITIALIZATION -> TODO()
        }

        return mapper.map(from) as Observable<EventInfo<*>>
    }

}