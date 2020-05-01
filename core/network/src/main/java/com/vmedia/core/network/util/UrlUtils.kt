package com.vmedia.core.network.util

/**
 * Adds "https:" to the start
 *
 * Some urls received from the backend have wrong format, so we should fix it
 *
 * @receiver url received from the backend
 * @return the same url starts with "http:"
 */
internal fun String.fixUrl(): String {
    return when {
        startsWith("http") -> this
        isBlank() -> this
        else -> "https:$this"
    }
}