package com.vmedia.core.sync.event

import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.util.EMPTY_DATE
import java.util.*

class EventModel(
    val type: EventType,
    val date: Date = EMPTY_DATE,
    val entities: Collection<Long> = emptyList()
)