package com.vmedia.core.common.obj

import java.math.BigDecimal

data class Money(
    val currency: Currency,
    val value: BigDecimal
)

enum class Currency(val symbol: Char) {
    USD('$'),
    EUR('€'),
    YUAN('¥'),
    RUB('₽')
}

fun Char.toCurrency() : Currency {
    return Currency.values().find { it.symbol == this } ?: Currency.USD
}