package com.vmedia.core.common.obj

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Money(
    val currency: Currency,
    val value: BigDecimal
): Parcelable

enum class Currency(val symbol: Char) {
    USD('$'),
    EUR('€'),
    YUAN('¥'),
    RUB('₽')
}

fun Char.toCurrency() : Currency {
    return Currency.values().find { it.symbol == this } ?: Currency.USD
}