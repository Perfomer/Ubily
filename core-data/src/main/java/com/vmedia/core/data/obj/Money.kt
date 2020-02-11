package com.vmedia.core.data.obj

import java.math.BigDecimal

data class Money(
    val currency: Currency,
    val value: BigDecimal
)

enum class Currency {
    US_DOLLAR,
    EURO,
    CH_YUAN,
    RU_RUBLE
}