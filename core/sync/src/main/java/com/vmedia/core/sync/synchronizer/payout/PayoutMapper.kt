package com.vmedia.core.sync.synchronizer.payout

import com.vmedia.core.common.pure.util.Mapper
import com.vmedia.core.data.internal.database.entity.Payout
import com.vmedia.core.network.entity.internal.IncomeDto
import com.vmedia.core.sync._PeriodIdProvider

internal class PayoutMapper(
    private val periodIdProvider: _PeriodIdProvider
) : Mapper<IncomeDto.Payout, Payout> {

    override fun map(from: IncomeDto.Payout): Payout {
        val periodId = periodIdProvider.invoke(from.period)

        return Payout(
            periodId = periodId,
            date = from.date,
            valueUsd = from.value.value.abs(),
            autoPayout = from.autoPayout,
            paypal = from.paypal,
            isFailed = from.isFailed
        )
    }

}