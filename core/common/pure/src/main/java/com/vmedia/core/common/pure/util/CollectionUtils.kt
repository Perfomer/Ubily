package com.vmedia.core.common.pure.util

fun <T, R> List<T>.zipWithNullable(otherSource: List<R>?): List<Pair<T, R?>> {
    val result = mutableListOf<Pair<T, R?>>()

    for (i in indices) {
        result += this[i] to otherSource?.getOrNull(i)
    }

    return result
}

fun <T, R : Comparable<R>> Collection<T>.maxValue(extractor: (T) -> R): R {
    val maxItem = maxBy { extractor.invoke(it) }!!
    return extractor.invoke(maxItem)
}

fun <T> Sequence<T>.onEachIndexed(action: (index: Int, item: T) -> Unit): Sequence<T> {
    return mapIndexed { index: Int, item: T ->
        action(index, item)
        item
    }
}