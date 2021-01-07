package com.vmedia.feature.auth.data.browser

import android.webkit.WebView
import com.vmedia.core.common.android.BuildConfig
import com.vmedia.feature.auth.data.browser.controller.AuthController
import com.vmedia.feature.auth.data.browser.controller.invokeController
import com.vmedia.feature.auth.domain.SignInTask
import io.reactivex.Completable

internal class UnityWebViewSignInTask(private val webView: WebView) : SignInTask {

    override fun signIn(login: String, password: String): Completable {
        return UnitySignInCompletable(
            webView,
            AuthController(login, password)
        )
    }

}

private class UnitySignInCompletable(
    webView: WebView,
    private val authController: AuthController
) : BrowserRequestCompletable(webView, BuildConfig.NETWORK_AUTHORIZED_URL) {

    override fun onPageLoaded(url: String) {
        when {
            url.isPackagesPageUrl() -> observer?.onComplete()
            url.isAuthPageUrl() -> webView.invokeController(authController)
            else -> observer?.onError(IllegalStateException())
        }
    }

    private companion object {
        private fun String.isPackagesPageUrl() = equals(BuildConfig.NETWORK_AUTHORIZED_URL)
        private fun String.isAuthPageUrl() = contains(BuildConfig.NETWORK_LOGIN_URL_PART)

    }

}
