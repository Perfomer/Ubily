package com.vmedia.core.data.internal.database.converter

import androidx.room.TypeConverter
import java.util.*

@Suppress("unused")
class DateConverter {

    @TypeConverter
    fun fromDate(date: Date) = date.time

    @TypeConverter
    fun fromTimestamp(timestamp: Long) = Date(timestamp)

}