package com.vmedia.core.network.util

private const val BYTES_IN_MEGABYTE = 0x100000

internal fun Long.bytesToMegabytes(): Double {
    return this / BYTES_IN_MEGABYTE.toDouble()
}