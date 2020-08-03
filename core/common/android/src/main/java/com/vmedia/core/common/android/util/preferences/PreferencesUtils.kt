package com.vmedia.core.common.android.util.preferences

@Suppress("IMPLICIT_CAST_TO_ANY")
inline fun <reified T> getPreferencesDefaultValue(): T {
    val clazz = T::class

    return when (clazz) {
        String::class -> ""
        Boolean::class -> false
        Int::class -> 0
        Float::class -> 0f
        Long::class -> 0L
        Set::class -> emptySet<String>()
        else -> throw IllegalStateException("T generic param is unsupported: ${clazz.simpleName}")
    } as T
}