package com.vmedia.core.data.repository.event.mapper

import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.obj.EventInfo.EventListInfo.EventSale
import com.vmedia.core.data.obj.SaleInfo

internal class SaleMapper(
    source: DatabaseDataSource
) : ProvisionMapper<EventSale>(source) {

    override fun createEventInfo(event: Event, content: List<SaleInfo>): EventSale {
        return EventSale(
            id = event.id,
            date = event.date,
            content = content
        )
    }

}