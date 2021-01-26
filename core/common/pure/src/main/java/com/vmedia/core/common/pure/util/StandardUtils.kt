package com.vmedia.core.common.pure.util

fun <T : Any> T.applyIf(condition: Boolean, body: T.() -> Unit): T {
    if (condition) apply(body)
    return this
}
