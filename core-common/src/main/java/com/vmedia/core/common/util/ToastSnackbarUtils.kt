package com.vmedia.core.common.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Context.toast(@StringRes stringId: Int, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, stringId, duration).show()
}

fun Context.toast(text: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, duration).show()
}

fun Context.toast(obj: Any, duration: Int = Toast.LENGTH_LONG) {
    toast(obj.toString(), duration)
}

fun Fragment.toast(@StringRes stringId: Int, duration: Int = Toast.LENGTH_LONG) {
    context?.toast(stringId, duration)
}

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_LONG) {
    context?.toast(text, duration)
}

fun Fragment.toast(obj: Any, duration: Int = Toast.LENGTH_LONG) {
    toast(obj.toString(), duration)
}



fun View.snackbar(text: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, text, duration).show()
}

fun View.snackbar(@StringRes textResource: Int, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, textResource, duration).show()
}

fun Fragment.snackbar(text: String, duration: Int = Snackbar.LENGTH_LONG) {
    view?.snackbar(text, duration)
}

fun Fragment.snackbar(@StringRes textResource: Int, duration: Int = Snackbar.LENGTH_LONG) {
    view?.snackbar(textResource, duration)
}