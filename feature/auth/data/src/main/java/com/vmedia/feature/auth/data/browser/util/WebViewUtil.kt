package com.vmedia.feature.auth.data.browser.util

import android.webkit.WebView

internal fun WebView.buildScript() = JsScriptBuilder(::evaluateScript)

internal fun WebView.evaluateScript(script: String) {
    evaluateJavascript("javascript: { $script }", null)
}