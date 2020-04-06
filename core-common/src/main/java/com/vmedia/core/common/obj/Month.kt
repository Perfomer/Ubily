package com.vmedia.core.common.obj

import androidx.annotation.StringRes
import com.vmedia.core.common.R
import com.vmedia.core.common.obj.Month.*

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

val Month.labelResource: Int
    @StringRes
    get() = when (this) {
        JANUARY -> R.string.month_january
        FEBRUARY -> R.string.month_february
        MARCH -> R.string.month_march
        APRIL -> R.string.month_april
        MAY -> R.string.month_may
        JUNE -> R.string.month_june
        JULY -> R.string.month_july
        AUGUST -> R.string.month_august
        SEPTEMBER -> R.string.month_september
        OCTOBER -> R.string.month_october
        NOVEMBER -> R.string.month_november
        DECEMBER -> R.string.month_december
    }