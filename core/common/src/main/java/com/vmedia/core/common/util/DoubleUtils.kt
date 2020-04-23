package com.vmedia.core.common.util

fun Double.cropToString(
    signsAfterDot: Int = 2,
    cropZeros: Boolean = true
): String {
    return buildString {
        append(String.format("%.${signsAfterDot}f", this@cropToString))

        while (last() == '0') {
            deleteLast()
        }

        if (last() == '.') {
            deleteLast()
        }
    }
}