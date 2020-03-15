package com.vmedia.core.common.util

fun <K, V> Map<K, V>.get(key: K, default: V): V {
    return get(key) ?: default
}