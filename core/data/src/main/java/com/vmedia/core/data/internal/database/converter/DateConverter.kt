package com.vmedia.core.data.internal.database.converter

import androidx.room.TypeConverter
import java.util.*

internal class DateConverter : Converter<Date, Long> {

    @TypeConverter
    override fun to(item: Date) = item.time

    @TypeConverter
    override fun from(source: Long) = Date(source)

}