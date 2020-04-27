package com.vmedia.core.common.util

fun Double.cropToString(
    signsAfterDot: Int = 2,
    cropZeros: Boolean = true
): String {
    return buildString(format(signsAfterDot)) {
        if (cropZeros) {
            while (last() == '0') deleteLast()
            if (last() == '.') deleteLast()
        }
    }
}

fun Double.format(signsAfterDot: Int): String {
    return String.format("%.${signsAfterDot}f", this)
}