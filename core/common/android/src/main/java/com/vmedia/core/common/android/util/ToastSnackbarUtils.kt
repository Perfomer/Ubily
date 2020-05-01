package com.vmedia.core.common.android.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

var Snackbar.isVisible: Boolean
    get() = isShown
    set(value) {
        if (value) show()
        else dismiss()
    }

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

fun View.createSnackbar(
    text: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    actionText: CharSequence? = null,
    action: (() -> Unit)? = null
): Snackbar {
    val snackbar = Snackbar.make(this, text, duration)

    if (action != null && actionText != null) {
        snackbar.setAction(actionText) { action.invoke() }
    }

    return snackbar
}

fun View.createSnackbar(
    @StringRes textResource: Int,
    duration: Int = Snackbar.LENGTH_LONG,
    @StringRes actionTextResource: Int? = null,
    action: (() -> Unit)? = null
): Snackbar {
    return createSnackbar(
        text = resources.getText(textResource),
        duration = duration,
        actionText = actionTextResource?.let(resources::getText),
        action = action
    )
}

fun Fragment.createSnackbar(
    text: CharSequence,
    duration: Int = Snackbar.LENGTH_LONG,
    actionText: CharSequence? = null,
    action: (() -> Unit)? = null
): Snackbar {
    return view!!.createSnackbar(text, duration, actionText, action)
}

fun Fragment.createSnackbar(
    @StringRes textResource: Int,
    duration: Int = Snackbar.LENGTH_LONG,
    @StringRes actionTextResource: Int? = null,
    action: (() -> Unit)? = null
): Snackbar {
    return view!!.createSnackbar(textResource, duration, actionTextResource, action)
}
