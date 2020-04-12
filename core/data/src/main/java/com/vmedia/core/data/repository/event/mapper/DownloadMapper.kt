package com.vmedia.core.data.repository.event.mapper

import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.obj.EventInfo.EventListInfo.EventFreeDownload
import com.vmedia.core.data.obj.SaleInfo

internal class DownloadMapper(
    source: DatabaseDataSource
) : ProvisionMapper<EventFreeDownload>(source) {

    override fun createEventInfo(event: Event, content: List<SaleInfo>): EventFreeDownload {
        return EventFreeDownload(
            id = event.id,
            date = event.date,
            content = content
        )
    }

}