package com.vmedia.core.common.android.util

import androidx.annotation.StringRes
import com.vmedia.core.common.android.R
import com.vmedia.core.common.pure.obj.Month

val Month.labelResource: Int
    @StringRes
    get() = when (this) {
        Month.JANUARY -> R.string.month_january
        Month.FEBRUARY -> R.string.month_february
        Month.MARCH -> R.string.month_march
        Month.APRIL -> R.string.month_april
        Month.MAY -> R.string.month_may
        Month.JUNE -> R.string.month_june
        Month.JULY -> R.string.month_july
        Month.AUGUST -> R.string.month_august
        Month.SEPTEMBER -> R.string.month_september
        Month.OCTOBER -> R.string.month_october
        Month.NOVEMBER -> R.string.month_november
        Month.DECEMBER -> R.string.month_december
    }