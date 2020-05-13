package com.vmedia.core.common.pure.obj.event

import com.vmedia.core.common.pure.obj.Period
import java.math.BigDecimal

data class PayoutInfo(
    val period: Period,
    val amount: BigDecimal = BigDecimal.ZERO,
    val auto: Boolean = false,
    val paypal: Boolean = false,
    val failed: Boolean = false
)