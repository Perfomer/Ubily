package com.vmedia.core.common.obj

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