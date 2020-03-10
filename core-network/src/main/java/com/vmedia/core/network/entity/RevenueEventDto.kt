package com.vmedia.core.network.entity

import com.vmedia.core.common.obj.Money
import com.vmedia.core.common.obj.Period
import java.util.*

sealed class RevenueEventDto {

    abstract val date: Date
    abstract val period: Period

    data class Revenue(
        override val date: Date,
        override val period: Period,
        val debit: Money,
        val balance: Money,
        val isSale: Boolean
    ) : RevenueEventDto()

    data class FixingRevenue(
        override val date: Date,
        override val period: Period,
        val credit: Money,
        val balance: Money,
        val isSale: Boolean
    ) : RevenueEventDto()

    data class Payout(
        override val date: Date,
        override val period: Period,
        val credit: Money,
        val autoPayout: Boolean,
        val paypal: Boolean
    ) : RevenueEventDto()

    data class FailedPayout(
        override val date: Date,
        override val period: Period,
        val debit: Money,
        val balance: Money
    ) : RevenueEventDto()

}