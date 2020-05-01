package com.vmedia.core.common.android.util

fun StringBuilder.deleteLast(): StringBuilder {
    return deleteCharAt(length - 1)
}

/**
 * Builds new string by populating newly created [StringBuilder] using provided [builderAction]
 * and then converting it to [String].
 */
inline fun buildString(initialString: String, builderAction: StringBuilder.() -> Unit): String {
    return StringBuilder(initialString).apply(builderAction).toString()
}