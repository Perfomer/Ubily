package com.vmedia.core.common.util

import androidx.annotation.WorkerThread
import io.reactivex.Single

/**
 * Filter interface
 * Returns new list with the filtered items
 *
 * @param T list items type
 */
@FunctionalInterface
interface Filter<T> {

    @WorkerThread
    fun filter(source: List<T>): List<T>

}

/**
 * Item Filter class
 * Implements [Filter] interface
 */
abstract class ItemFilter<T> : Filter<T> {

    @WorkerThread
    final override fun filter(source: List<T>): List<T> {
        return source.filter(::filter)
    }

    abstract fun filter(item: T): Boolean

}

fun <T> Single<List<T>>.filterWith(filter: Filter<T>) = map(filter::filter)