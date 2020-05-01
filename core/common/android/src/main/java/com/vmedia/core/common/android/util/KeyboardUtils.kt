package com.vmedia.core.common.android.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    requestFocus()

    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun Fragment.hideKeyboard() {
    val fragmentView = this.view ?: return
    val rootView = fragmentView.rootView

    rootView.hideKeyboard()
}

fun Fragment.showKeyboard() {
    val fragmentView = this.view ?: return
    val rootView = fragmentView.rootView

    rootView.requestFocus()
    rootView.showKeyboard()
}