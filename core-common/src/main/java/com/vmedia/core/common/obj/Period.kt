package com.vmedia.core.common.obj

import java.util.*

data class Period(val year: Int, val month: Month) {

    val startTimestamp: Long by lazy { startCalendar.timeInMillis }
    val endTimestamp: Long by lazy { endCalendar.timeInMillis }

    // todo tests
    override fun toString(): String {
        val monthValue = month.ordinal + 1
        val stub = if (monthValue < 10) "0" else ""
        return "$year$stub$monthValue"
    }

}

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

enum class Month {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER
}

/**
 * Creates (every time) new [Calendar] instance for the end of the received [Period]
 */
private val Period.endCalendar: Calendar
    get() = startCalendar.apply {
        add(Calendar.MONTH, 1)
        add(Calendar.SECOND, -1)
    }

/**
 * Creates (every time) new [Calendar] instance for the start of the received [Period]
 */
private val Period.startCalendar: Calendar
    get() = emptyCalendar.apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month.ordinal)
    }

/**
 * Creates (every time) new [Calendar] instance with following settings:
 * * year & month are set to current
 * * day of month is set to 1st
 * * minutes, seconds, milliseconds are set to 0
 */
private val emptyCalendar: Calendar
    get() = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)

        clear(Calendar.MINUTE)
        clear(Calendar.SECOND)
        clear(Calendar.MILLISECOND)
    }