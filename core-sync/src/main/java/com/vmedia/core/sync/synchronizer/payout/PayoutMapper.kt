package com.vmedia.core.sync.synchronizer.payout

import com.vmedia.core.common.util.Mapper
import com.vmedia.core.data.internal.database.entity.Payout
import com.vmedia.core.network.entity.RevenueEventDto
import com.vmedia.core.sync._PeriodIdProvider

internal class PayoutMapper(
    private val periodIdProvider: _PeriodIdProvider
) : Mapper<RevenueEventDto, Payout> {

    override fun map(from: RevenueEventDto): Payout {
        val periodId by lazy { periodIdProvider.invoke(from.period) }

        return when (from) {
            is RevenueEventDto.Payout -> {
                Payout(
                    periodId = periodId,
                    date = from.date,
                    valueUsd = from.credit.value,
                    autoPayout = from.autoPayout,
                    paypal = from.paypal,
                    isFailed = false
                )
            }

            is RevenueEventDto.FailedPayout -> {
                Payout(
                    periodId = periodId,
                    date = from.date,
                    valueUsd = from.debit.value,
                    autoPayout = false,
                    paypal = false,
                    isFailed = true
                )
            }

            else -> {
                throw IllegalArgumentException("Received object is not Payout or FailedPayout: $from")
            }
        }
    }

}