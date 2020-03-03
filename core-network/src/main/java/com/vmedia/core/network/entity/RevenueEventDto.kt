package com.vmedia.core.network.entity

import com.vmedia.core.common.obj.Money
import java.util.*

sealed class RevenueEventDto {

    abstract val date: Date

    data class Revenue(
        override val date: Date,
        val debit: Money,
        val balance: Money,
        val isSale: Boolean
    ) : RevenueEventDto()

    data class FixingRevenue(
        override val date: Date,
        val credit: Money,
        val balance: Money,
        val isSale: Boolean
    ) : RevenueEventDto()

    data class Payout(
        override val date: Date,
        val credit: Money,
        val autoPayout: Boolean,
        val paypal: Boolean
    ) : RevenueEventDto()

    data class FailedPayout(
        override val date: Date,
        val debit: Money,
        val balance: Money
    ) : RevenueEventDto()

}