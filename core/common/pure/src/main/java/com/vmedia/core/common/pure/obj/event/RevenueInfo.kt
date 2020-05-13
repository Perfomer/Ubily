package com.vmedia.core.common.pure.obj.event

import com.vmedia.core.common.pure.obj.Period
import java.math.BigDecimal

data class RevenueInfo(
    val period: Period,
    val periodId: Long = 0L,
    val amount: BigDecimal = BigDecimal.ZERO,
    val sale: Boolean = false,
    val revenueDelta: Double = 0.0
)