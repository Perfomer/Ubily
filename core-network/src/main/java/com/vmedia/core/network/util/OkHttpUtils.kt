package com.vmedia.core.network.util

import okhttp3.OkHttpClient

internal fun OkHttpClient.Builder.addCookieInterceptor(cookiesProvider: () -> Array<Pair<String, String>>): OkHttpClient.Builder {
    return addInterceptor { chain ->
        val header = cookiesProvider.invoke().formatCookies()
        val request = chain.request()
            .newBuilder()
            .addHeader("Cookie", header)
            .build()

        chain.proceed(request)
    }
}

private fun Array<Pair<String, String>>.formatCookies(): String {
    val result = StringBuilder()

    for (cookie in this) {
        result.append(cookie.first)
            .append('=')
            .append(cookie.second)
            .append(';')
    }

    return result.toString()
}