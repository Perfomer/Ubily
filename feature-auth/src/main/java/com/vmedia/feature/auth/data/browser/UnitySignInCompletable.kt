package com.vmedia.feature.auth.data.browser

import android.webkit.WebView
import com.vmedia.feature.auth.data.browser.controllers.AuthController
import com.vmedia.feature.auth.data.browser.controllers.invokeController

internal fun WebView.unitySignIn(login: String, password: String): UnitySignInCompletable {
    return UnitySignInCompletable(this, AuthController(login, password))
}

internal class UnitySignInCompletable(
    webView: WebView,
    private val authController: AuthController
) : BrowserRequestCompletable(webView, PACKAGES_URL) {

    override fun onPageLoaded(url: String) {
        when {
            url.isPackagesPageUrl() -> observer?.onComplete()
            url.isAuthPageUrl() -> webView.invokeController(authController)
        }
    }

    private companion object {

        private const val PACKAGES_URL = "https://publisher.assetstore.unity3d.com/packages.html"
        private const val LOGIN_URL_PART = "id.unity.com"

        private fun String.isPackagesPageUrl() = equals(PACKAGES_URL)
        private fun String.isAuthPageUrl() = contains(LOGIN_URL_PART)

    }

}