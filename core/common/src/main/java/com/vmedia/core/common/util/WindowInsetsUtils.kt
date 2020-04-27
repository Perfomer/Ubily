package com.vmedia.core.common.util

import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vmedia.core.common.util.InsetDirection.*

typealias OnApplyWindowInsets = (view: View, insets: WindowInsetsCompat, initialPadding: Rect) -> WindowInsetsCompat

private val View.paddingRect: Rect
    get() = Rect(paddingLeft, paddingTop, paddingRight, paddingBottom)


fun View.addSystemPadding(
    targetView: View = this,
    isConsumed: Boolean = false,
    vararg direction: InsetDirection
) = doOnApplyWindowInsets { _, insets, initialPadding ->
    direction.forEach {
        val updatedPadding = initialPadding.extract(it) + insets.extract(it)
        targetView.updatePadding(it, updatedPadding)
    }

    if (isConsumed) {
        insets.apply { direction.forEach { updateSystemWindowInsets(it, 0) } }
    } else {
        insets
    }
}

fun View.addSystemVerticalPadding(
    targetView: View = this,
    isConsumed: Boolean = false
) = addSystemPadding(targetView, isConsumed, TOP, BOTTOM)

fun View.addSystemTopPadding(
    targetView: View = this,
    isConsumed: Boolean = false
) = addSystemPadding(targetView, isConsumed, TOP)

fun View.addSystemBottomPadding(
    targetView: View = this,
    isConsumed: Boolean = false
) = addSystemPadding(targetView, isConsumed, BOTTOM)

fun View.doOnApplyWindowInsets(block: OnApplyWindowInsets) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, paddingRect)
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

private fun View.updatePadding(direction: InsetDirection, value: Int) {
    setPadding(
        if (direction == LEFT) value else paddingLeft,
        if (direction == TOP) value else paddingTop,
        if (direction == RIGHT) value else paddingRight,
        if (direction == BOTTOM) value else paddingBottom
    )
}

private fun WindowInsetsCompat.updateSystemWindowInsets(
    direction: InsetDirection,
    value: Int
): WindowInsetsCompat {
    return replaceSystemWindowInsets(
        if (direction == LEFT) value else systemWindowInsetLeft,
        if (direction == TOP) value else systemWindowInsetTop,
        if (direction == RIGHT) value else systemWindowInsetRight,
        if (direction == BOTTOM) value else systemWindowInsetBottom
    )
}

private fun Rect.extract(direction: InsetDirection) = when (direction) {
    LEFT -> left
    TOP -> top
    RIGHT -> right
    BOTTOM -> bottom
}

private fun WindowInsetsCompat.extract(direction: InsetDirection) = when (direction) {
    LEFT -> systemWindowInsetLeft
    TOP -> systemWindowInsetTop
    RIGHT -> systemWindowInsetRight
    BOTTOM -> systemWindowInsetBottom
}

enum class InsetDirection {
    LEFT,
    TOP,
    RIGHT,
    BOTTOM
}