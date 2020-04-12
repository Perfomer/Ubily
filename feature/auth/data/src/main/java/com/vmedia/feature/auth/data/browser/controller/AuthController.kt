package com.vmedia.feature.auth.data.browser.controller

import android.webkit.WebView
import com.vmedia.feature.auth.data.browser.util.buildScript

internal class AuthController(
    private val login: String,
    private val password: String
): WebViewController {

    override fun invoke(webView: WebView) {
        webView.buildScript()
            .fill(FIELD_LOGIN, login)
            .fill(FIELD_PASSWORD, password)
            .click(BUTTON_SIGNIN)
            .evaluate()
    }

    private companion object {

        private const val FIELD_LOGIN = "conversations_create_session_form_email"
        private const val FIELD_PASSWORD = "conversations_create_session_form_password"
        private const val BUTTON_SIGNIN = "commit"

    }

}