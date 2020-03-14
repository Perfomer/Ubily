package com.vmedia.core.sync.synchronizer.revenue

import com.vmedia.core.common.util.Mapper
import com.vmedia.core.data.internal.database.entity.Revenue
import com.vmedia.core.network.entity.RevenueEventDto
import com.vmedia.core.sync._PeriodIdProvider

internal class RevenueMapper(
    private val periodIdProvider: _PeriodIdProvider
) : Mapper<RevenueEventDto, Revenue> {

    override fun map(from: RevenueEventDto): Revenue {
        val periodId by lazy { periodIdProvider.invoke(from.period) }

        return when (from) {
            is RevenueEventDto.Revenue -> {
                Revenue(
                    periodId = periodId,
                    date = from.date,
                    valueUsd = from.debit.value,
                    isSale = from.isSale,
                    isFixing = false
                )
            }

            is RevenueEventDto.FixingRevenue -> {
                Revenue(
                    periodId = periodId,
                    date = from.date,
                    valueUsd = from.credit.value,
                    isSale = from.isSale,
                    isFixing = true
                )
            }

            else -> {
                throw IllegalArgumentException("Received object is not Revenue or FixingRevenue: $from")
            }
        }

    }

}