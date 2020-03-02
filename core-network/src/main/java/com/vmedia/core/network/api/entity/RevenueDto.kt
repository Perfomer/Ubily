package com.vmedia.core.network.api.entity

import com.vmedia.core.common.obj.Money
import java.util.*

data class RevenueDto(
    val date: Date,
    val description: String,
    val debit: Money?,
    val credit: Money?,
    val balance: Money?
)