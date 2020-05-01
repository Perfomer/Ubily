package com.vmedia.core.common.android.util

import android.content.Context
import com.vmedia.core.common.pure.obj.Period

fun Period.getString(context: Context): String {
    return "${context.getString(month.labelResource)} $year"
}