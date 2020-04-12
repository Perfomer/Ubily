package com.vmedia.feature.auth.data.browser

import android.annotation.SuppressLint
import android.webkit.WebView
import com.vmedia.feature.auth.data.browser.util.RoutableWebClient
import io.reactivex.Completable
import io.reactivex.CompletableObserver

internal abstract class BrowserRequestCompletable(
    protected val webView: WebView,
    private val url: String
) : Completable() {

    protected open val maxTryCount = DEFAULT_MAX_TRY_COUNT

    protected var observer: CompletableObserver? = null

    private var lastUrl: String = ""
    private var tryCount = 0

    @SuppressLint("SetJavaScriptEnabled")
    override fun subscribeActual(observer: CompletableObserver?) {
        this.observer = observer

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = RoutableWebClient(::onPageFinished, ::onErrorReceived)

        webView.loadUrl(url)
    }

    @Synchronized
    private fun onPageFinished(url: String) {
        if (lastUrl == url) {
            if (tryCount >= maxTryCount) observer?.onError(Exception("Failed $maxTryCount times"))
            else tryCount++

            return
        }

        lastUrl = url

        onPageLoaded(url)
    }

    protected abstract fun onPageLoaded(url: String)

    private fun onErrorReceived(errorMessage: String) {
        observer?.onError(Exception(errorMessage))
    }

    private companion object {
        private const val DEFAULT_MAX_TRY_COUNT = 3
    }

}