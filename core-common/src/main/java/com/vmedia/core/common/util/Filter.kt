package com.vmedia.core.common.util

import io.reactivex.Single

/**
 * Filter interface
 * Returns new list with the filtered items
 *
 * @param T list items type
 */
@FunctionalInterface
interface Filter<T> {
    fun filter(source: List<T>): List<T>
}

fun <T> Single<List<T>>.filterWith(filter: Filter<T>) = map(filter::filter)