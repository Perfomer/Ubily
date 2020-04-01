package com.vmedia.core.common.util

import java.math.BigDecimal

operator fun BigDecimal.times(other: Int) = multiply(BigDecimal(other))

/**
 * Returns the sum of all values produced by [selector] function applied to each element in the collection.
 */
inline fun <T> Iterable<T>.sumByBigDecimal(selector: (T) -> BigDecimal): BigDecimal {
    var sum = BigDecimal.ZERO

    for (element in this) {
        sum += selector(element)
    }

    return sum
}