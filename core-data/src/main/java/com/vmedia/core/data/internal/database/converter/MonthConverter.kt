package com.vmedia.core.data.internal.database.converter

import androidx.room.TypeConverter
import com.vmedia.core.common.obj.Month

class MonthConverter : StringConverter<Month> {

    @TypeConverter
    override fun to(item: Month) = item.name

    @TypeConverter
    override fun from(source: String) = Month.valueOf(source)

}