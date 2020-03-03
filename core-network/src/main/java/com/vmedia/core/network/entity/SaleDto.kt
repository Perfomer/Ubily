package com.vmedia.core.network.entity

import com.vmedia.core.common.obj.Money
import java.util.*

data class SaleDto(
    val assetName: String,
    val price: Money,
    val salesQuantity: Int,
    val refundsQuantity: Int,
    val chargebacksQuantity: Int,
    val firstSale: Date,
    val lastSale: Date
)