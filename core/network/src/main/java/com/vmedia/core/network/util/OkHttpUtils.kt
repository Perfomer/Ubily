package com.vmedia.core.network.util

import okhttp3.OkHttpClient

internal fun OkHttpClient.Builder.addCookieInterceptor(cookiesProvider: () -> Array<Cookie>): OkHttpClient.Builder {
    return addInterceptor { chain ->
        val header = cookiesProvider.invoke().formatCookies()
        val request = chain.request()
            .newBuilder()
            .addHeader("Cookie", header)
            .build()

        chain.proceed(request)
    }
}

private fun Array<Cookie>.formatCookies(): String {
    val result = StringBuilder()

    for (cookie in this) {
        result.append(cookie.title)
            .append('=')
            .append(cookie.value)
            .append(';')
    }

    return result.toString()
}