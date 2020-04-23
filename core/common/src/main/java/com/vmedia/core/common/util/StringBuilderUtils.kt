package com.vmedia.core.common.util

fun StringBuilder.deleteLast(): StringBuilder {
    return deleteCharAt(length - 1)
}