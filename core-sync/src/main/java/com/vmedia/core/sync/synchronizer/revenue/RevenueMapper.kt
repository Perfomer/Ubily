package com.vmedia.core.sync.synchronizer.revenue

import com.vmedia.core.common.util.Mapper
import com.vmedia.core.data.internal.database.entity.Revenue
import com.vmedia.core.network.entity.internal.IncomeDto
import com.vmedia.core.sync._PeriodIdProvider

internal class RevenueMapper(
    private val periodIdProvider: _PeriodIdProvider
) : Mapper<IncomeDto.Revenue, Revenue> {

    override fun map(from: IncomeDto.Revenue): Revenue {
        val periodId = periodIdProvider.invoke(from.period)

        return Revenue(
            periodId = periodId,
            date = from.date,
            valueUsd = from.value.value,
            isSale = from.isSale
        )
    }

}