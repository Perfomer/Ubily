package com.vmedia.core.network.entity.internal

import com.vmedia.core.common.obj.Money
import com.vmedia.core.common.obj.Period
import java.util.*

sealed class IncomeDto {

    abstract val date: Date
    abstract val period: Period
    abstract val value: Money

    data class Revenue(
        override val date: Date,
        override val period: Period,
        override val value: Money,
        val isSale: Boolean
    ) : IncomeDto()

    data class Payout(
        override val date: Date,
        override val period: Period,
        override val value: Money,
        val autoPayout: Boolean,
        val paypal: Boolean,
        val isFailed: Boolean
    ) : IncomeDto()

}