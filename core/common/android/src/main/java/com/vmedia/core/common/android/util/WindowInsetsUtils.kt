package com.vmedia.core.common.android.util

import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

typealias OnApplyWindowInsets = (view: View, insets: WindowInsetsCompat, initialPadding: Rect) -> WindowInsetsCompat
typealias OnWindowInsetsChanged = (() -> Unit)

private val View.paddingRect: Rect
    get() = Rect(paddingLeft, paddingTop, paddingRight, paddingBottom)

fun View.addSystemVerticalPadding(
    targetView: View = this,
    isConsumed: Boolean = false,
    onWindowInsetsChanged: OnWindowInsetsChanged? = null
) {
    doOnApplyWindowInsets { _, insets, initialPadding ->
        targetView.updatePadding(
            top = initialPadding.top + insets.systemWindowInsetTop,
            bottom = initialPadding.bottom + insets.systemWindowInsetBottom
        )

        onWindowInsetsChanged?.invoke()

        if (isConsumed) {
            insets.updateSystemWindowInsets(top = 0, bottom = 0)
        } else {
            insets
        }
    }
}

fun View.addSystemTopPadding(
    targetView: View = this,
    isConsumed: Boolean = false,
    onWindowInsetsChanged: OnWindowInsetsChanged? = null
) {
    doOnApplyWindowInsets { _, insets, initialPadding ->
        targetView.updatePadding(top = initialPadding.top + insets.systemWindowInsetTop)

        onWindowInsetsChanged?.invoke()

        if (isConsumed) {
            insets.updateSystemWindowInsets(top = 0)
        } else {
            insets
        }
    }
}

fun View.addSystemBottomPadding(
    targetView: View = this,
    isConsumed: Boolean = false,
    onWindowInsetsChanged: OnWindowInsetsChanged? = null
) {
    doOnApplyWindowInsets { _, insets, initialPadding ->
        targetView.updatePadding(bottom = initialPadding.bottom + insets.systemWindowInsetBottom)

        onWindowInsetsChanged?.invoke()

        if (isConsumed) {
            insets.updateSystemWindowInsets(bottom = 0)
        } else {
            insets
        }
    }
}

fun View.doOnApplyWindowInsets(block: OnApplyWindowInsets) {
    val initialPadding = paddingRect

    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, initialPadding)
    }

    requestApplyInsetsWhenAttached()
}


private fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        ViewCompat.requestApplyInsets(this)
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                ViewCompat.requestApplyInsets(v)
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

private fun WindowInsetsCompat.updateSystemWindowInsets(
    left: Int = systemWindowInsetLeft,
    top: Int = systemWindowInsetTop,
    right: Int = systemWindowInsetRight,
    bottom: Int = systemWindowInsetBottom
): WindowInsetsCompat {
    return replaceSystemWindowInsets(left, top, right, bottom)
}