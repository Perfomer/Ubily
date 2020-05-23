package com.vmedia.core.common.android.util

import android.view.View
import android.view.Window

fun Window.setSystemUiVisibilityFlag(flag: Int) {
    decorView.setSystemUiVisibilityFlag(flag)
}

fun Window.clearSystemUiVisibilityFlag(flag: Int) {
    decorView.clearSystemUiVisibilityFlag(flag)
}

fun View.setSystemUiVisibilityFlag(flag: Int) {
    systemUiVisibility = systemUiVisibility or flag
}

fun View.clearSystemUiVisibilityFlag(flag: Int) {
    systemUiVisibility = systemUiVisibility and flag.inv()
}