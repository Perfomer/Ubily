package com.vmedia.core.data.internal.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

internal class BigDecimalConverter : StringConverter<BigDecimal>() {

    @TypeConverter
    override fun from(source: String) = BigDecimal(source)

}