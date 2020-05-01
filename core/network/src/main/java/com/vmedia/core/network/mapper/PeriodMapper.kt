package com.vmedia.core.network.mapper

import com.vmedia.core.common.pure.obj.Month
import com.vmedia.core.common.pure.obj.Period
import com.vmedia.core.common.pure.util.Mapper
import com.vmedia.core.network.api.entity.PeriodModel

/**
 * Maps [PeriodModel] to [Period]
 * TODO tests
 * @receiver period model
 * @return period
 */
internal object PeriodMapper : Mapper<PeriodModel, Period> {

    override fun map(from: PeriodModel): Period {
        val value = from.value

        return Period(
            year = value.take(4).toInt(),
            month = Month.values()[value.takeLast(2).toInt() - 1]
        )
    }

}