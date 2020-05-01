package com.vmedia.feature.auth.data.util

import android.webkit.CookieManager

internal fun CookieManager.getCookie(url: String, cookieName: String): String? {
    val cookies = getCookie(url)
    if (cookies.isEmpty()) return null

    return cookies
        .split(';')
        .filter { it.contains(cookieName) }
        .map { it.split('=') }
        .map { it[1] }
        .firstOrNull()
}

internal fun CookieManager.clearCookies() {
    removeAllCookies(null)
    flush()
}