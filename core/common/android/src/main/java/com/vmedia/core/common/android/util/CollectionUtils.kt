package com.vmedia.core.common.android.util

import android.os.Build
import android.util.SparseArray
import android.util.SparseIntArray
import android.util.SparseLongArray
import androidx.annotation.RequiresApi

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

operator fun <E> SparseArray<E>.set(key: Int, value: E) = put(key, value)

operator fun SparseIntArray.set(key: Int, value: Int) = put(key, value)

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
operator fun SparseLongArray.set(key: Int, value: Long) = put(key, value)