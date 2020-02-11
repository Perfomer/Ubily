package com.vmedia.core.data.internal.database.converter

import androidx.room.TypeConverter
import com.vmedia.core.data.internal.database.entity.EventType

@Suppress("unused")
class EventTypeConverter {

    @TypeConverter
    fun fromEventType(eventType: EventType) = eventType.name

    @TypeConverter
    fun fromString(status: String) = EventType.valueOf(status)

}