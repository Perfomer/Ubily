package com.vmedia.core.data.internal.database.converter

import androidx.room.TypeConverter
import com.vmedia.core.common.obj.Currency

class CurrencyConverter: StringConverter<Currency> {

    @TypeConverter
    override fun to(item: Currency) = item.name

    @TypeConverter
    override fun from(source: String) = Currency.valueOf(source)

}