package com.vmedia.feature.auth.data.browser.util

import android.webkit.WebView
import android.webkit.WebViewClient

internal class RoutableWebClient(
    private val onPageFinished: (url: String) -> Unit,
    private val onReceivedError: (errorMessage: String) -> Unit
) : WebViewClient() {

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        onPageFinished.invoke(url)
    }

    @Suppress("DEPRECATION")
    override fun onReceivedError(
        view: WebView?,
        errorCode: Int,
        description: String?,
        failingUrl: String?
    ) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        onReceivedError.invoke(description ?: "no_error_message")
    }

}