package com.vmedia.core.sync.event

import com.vmedia.core.common.util.EMPTY_DATE
import com.vmedia.core.data.internal.database.entity.EventType
import java.util.*

class EventModel(
    val type: EventType,
    val date: Date = EMPTY_DATE,
    val entities: Collection<Long> = emptyList()
)