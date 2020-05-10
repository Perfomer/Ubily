package com.vmedia.core.common.android.util

import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
            insets.replaceSystemWindowInsets(
                insets.systemWindowInsetLeft,
                0,
                insets.systemWindowInsetRight,
                0
            )
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
        targetView.updatePadding(
            top = initialPadding.top + insets.systemWindowInsetTop
        )

        onWindowInsetsChanged?.invoke()

        if (isConsumed) {
            insets.replaceSystemWindowInsets(
                insets.systemWindowInsetLeft,
                0,
                insets.systemWindowInsetRight,
                insets.systemWindowInsetBottom
            )
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
        targetView.updatePadding(
            bottom = initialPadding.bottom + insets.systemWindowInsetBottom
        )

        onWindowInsetsChanged?.invoke()

        if (isConsumed) {
            insets.replaceSystemWindowInsets(
                insets.systemWindowInsetLeft,
                insets.systemWindowInsetTop,
                insets.systemWindowInsetRight,
                0
            )
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

private fun View.updatePadding(
    left: Int = paddingLeft,
    top: Int = paddingTop,
    right: Int = paddingRight,
    bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}