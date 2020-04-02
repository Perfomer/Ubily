package com.vmedia.core.common.util

fun <T> List<T>.split(predicate: (T) -> Boolean): Pair<List<T>, List<T>> {
    val trues = mutableListOf<T>()
    val falses = mutableListOf<T>()

    forEach {
        if (predicate.invoke(it)) trues += it
        else falses += it
    }

    return trues to falses
}

fun <T, R> List<T>.zipWithNullable(otherSource: List<R>?): List<Pair<T, R?>> {
    val result = mutableListOf<Pair<T, R?>>()

    for (i in indices) {
        result += this[i] to otherSource?.getOrNull(i)
    }

    return result
}

fun <T> List<T>.each(predicate: (T) -> Boolean): Boolean {
    return count(predicate) == size
}