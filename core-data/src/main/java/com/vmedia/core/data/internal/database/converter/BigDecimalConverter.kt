package com.vmedia.core.data.internal.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

@Suppress("unused")
class BigDecimalConverter {

    @TypeConverter
    fun fromBigDecimal(bigDecimal: BigDecimal) = bigDecimal.toString()

    @TypeConverter
    fun fromString(source: String) = BigDecimal(source)

}