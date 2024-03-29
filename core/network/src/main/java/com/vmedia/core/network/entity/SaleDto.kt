package com.vmedia.core.network.entity

import com.vmedia.core.common.pure.obj.Money
import java.util.*

data class SaleDto(
    val assetName: String,
    val assetUrl: String,
    val price: Money,
    val salesQuantity: Int,
    val refundsQuantity: Int,
    val chargebacksQuantity: Int,
    val firstSale: Date,
    val lastSale: Date
)