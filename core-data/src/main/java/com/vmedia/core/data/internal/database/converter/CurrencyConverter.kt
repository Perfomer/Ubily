package com.vmedia.core.data.internal.database.converter

import androidx.room.TypeConverter
import com.vmedia.core.common.obj.Currency

@Suppress("unused")
class CurrencyConverter {

    @TypeConverter
    fun fromCurrency(currency: Currency) = currency.name

    @TypeConverter
    fun fromString(source: String) = Currency.valueOf(source)

}