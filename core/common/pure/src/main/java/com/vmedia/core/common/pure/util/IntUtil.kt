package com.vmedia.core.common.pure.util

fun Int.applyBitwiseFlag(flag: Int, apply: Boolean = false): Int {
    return if (apply) this or flag
    else this and flag.inv()
}