package com.vmedia.core.network.mapper

import com.vmedia.core.common.obj.Month
import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.Mapper
import com.vmedia.core.network.api.entity.rest.PeriodModel

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