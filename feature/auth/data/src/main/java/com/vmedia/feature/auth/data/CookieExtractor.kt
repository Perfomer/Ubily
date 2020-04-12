package com.vmedia.feature.auth.data

import android.webkit.CookieManager

object CookieExtractor {

    private val cookieManager = CookieManager.getInstance()

    fun getCookie(url: String, cookieName: String): String? {
        val cookies = cookieManager.getCookie(url)
        if (cookies.isEmpty()) return null

        return cookies
            .split(';')
            .filter { it.contains(cookieName) }
            .map { it.split('=') }
            .map { it[1] }
            .firstOrNull()
    }

}