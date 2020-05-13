package com.vmedia.core.common.pure.obj.event

import java.math.BigDecimal

data class SaleInfo(
    val assetId: Long,
    val assetName: String = "",
    val assetIcon: String? = null,
    val quantity: Int = 1,
    val summaryPrice: BigDecimal = BigDecimal.ZERO
)