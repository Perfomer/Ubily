package com.vmedia.core.data.internal.database.converter

import androidx.room.TypeConverter
import com.vmedia.core.data.internal.database.entity.EventType

class EventTypeConverter : StringConverter<EventType> {

    @TypeConverter
    override fun to(item: EventType) = item.name

    @TypeConverter
    override fun from(source: String) = EventType.valueOf(source)

}