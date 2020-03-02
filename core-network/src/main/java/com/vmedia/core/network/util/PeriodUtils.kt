package com.vmedia.core.network.util

import com.vmedia.core.common.obj.Month
import com.vmedia.core.common.obj.Period
import com.vmedia.core.network.api.entity.rest.PeriodModel
import com.vmedia.core.network.api.entity.rest.PeriodsModel

/**
 * Creates a [Period] object
 *
 * Use like this: [Month.JANUARY of 2020]
 *
 * @receiver a month for [Period]
 * @param year a year for [Period]
 *
 * @return [Period] with provided month and year
 */
infix fun Month.of(year: Int): Period {
    return Period(year, this)
}

/**
 * Maps [PeriodsModel] to the [List] of [Period]s.
 *
 * @receiver [PeriodsModel]
 * @return list of [Period]s
 */
internal fun PeriodsModel.toPeriods(): List<Period> {
    return periods.map(PeriodModel::toPeriod)
}


/**
 * Maps [PeriodModel] to [Period]
 * TODO tests
 * @receiver period model
 * @return period
 */
internal fun PeriodModel.toPeriod(): Period {
    return Period(
        year = value.take(4).toInt(),
        month = Month.values()[value.takeLast(2).toInt() - 1]
    )
}