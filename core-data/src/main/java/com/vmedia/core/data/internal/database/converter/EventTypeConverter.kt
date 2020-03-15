package com.vmedia.core.data.internal.database.converter

import com.vmedia.core.data.internal.database.entity.EventType

internal class EventTypeConverter
    : EnumConverter<EventType>(EventType::class.java)