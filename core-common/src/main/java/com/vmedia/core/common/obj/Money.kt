package com.vmedia.core.common.obj

import java.math.BigDecimal

data class Money(
    val currency: Currency,
    val value: BigDecimal
)

enum class Currency(val symbol: String) {
    USD("$"),
    EUR("€"),
    YUAN("¥"),
    RUB("₽")
}