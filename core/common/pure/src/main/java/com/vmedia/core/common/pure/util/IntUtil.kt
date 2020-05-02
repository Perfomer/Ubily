package com.vmedia.core.common.pure.util

fun Int.or(other: Int, exclusive: Boolean = false): Int {
    return if (exclusive) this xor other
    else this or other
}