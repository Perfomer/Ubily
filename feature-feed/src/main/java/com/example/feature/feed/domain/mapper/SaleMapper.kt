package com.example.feature.feed.domain.mapper

import com.example.feature.feed.domain.FeedRepository
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.obj.EventInfo.EventListInfo.EventSale
import com.vmedia.core.data.obj.SaleInfo

internal class SaleMapper(
    repository: FeedRepository
) : ProvisionMapper<EventSale>(repository) {

    override fun createEventInfo(event: Event, content: List<SaleInfo>): EventSale {
        return EventSale(
            id = event.id,
            date = event.date,
            content = content
        )
    }

}