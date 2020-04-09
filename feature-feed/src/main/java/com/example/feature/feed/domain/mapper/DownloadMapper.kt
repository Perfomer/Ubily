package com.example.feature.feed.domain.mapper

import com.example.feature.feed.domain.FeedRepository
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.obj.EventInfo.EventListInfo.EventFreeDownload
import com.vmedia.core.data.obj.SaleInfo

internal class DownloadMapper(
    repository: FeedRepository
) : ProvisionMapper<EventFreeDownload>(repository) {

    override fun createEventInfo(event: Event, content: List<SaleInfo>): EventFreeDownload {
        return EventFreeDownload(
            id = event.id,
            date = event.date,
            content = content
        )
    }

}