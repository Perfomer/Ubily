package com.vmedia.feature.auth.data.browser.controllers

import android.webkit.WebView

internal interface WebViewController {
    fun invoke(webView: WebView)
}

internal fun WebView.invokeController(controller: WebViewController) {
    controller.invoke(this)
}