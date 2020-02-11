package com.vmedia.core.data.internal.database.converter

import androidx.room.TypeConverter
import com.vmedia.core.data.obj.Currency

@Suppress("unused")
class CurrencyConverter {

    @TypeConverter
    fun fromCurrency(currency: Currency) = currency.name

    @TypeConverter
    fun fromString(source: String) = Currency.valueOf(source)

}