package com.vmedia.core.data.internal.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalConverter : StringConverter<BigDecimal> {

    @TypeConverter
    override fun to(item: BigDecimal) = item.toString()

    @TypeConverter
    override fun from(source: String) = BigDecimal(source)

}