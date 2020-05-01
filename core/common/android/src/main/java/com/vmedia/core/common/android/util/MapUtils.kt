package com.vmedia.core.common.android.util

fun <K, V> Map<K, V>.get(key: K, default: V): V {
    return get(key) ?: default
}