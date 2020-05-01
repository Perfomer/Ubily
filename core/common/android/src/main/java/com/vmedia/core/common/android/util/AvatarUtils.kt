package com.vmedia.core.common.android.util

import androidx.annotation.ColorRes
import com.vmedia.core.common.android.R
import kotlin.math.abs

private val COLORS = arrayOf(
    R.color.additional_red,
    R.color.additional_pink,
    R.color.additional_purple,
    R.color.additional_blue,
    R.color.additional_blue_light,
    R.color.additional_turquoise,
    R.color.additional_orange,
    R.color.additional_yellow
)

@ColorRes
fun getUserColorResource(username: String): Int {
    return COLORS[abs(username.hashCode() % COLORS.size)]
}