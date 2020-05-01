package com.vmedia.feature.gallery.presentation.util

import android.content.Context
import android.util.TypedValue
import me.saket.flick.ContentSizeProvider2
import me.saket.flick.FlickCallbacks
import me.saket.flick.FlickGestureListener
import me.saket.flick.InterceptResult

private const val DEFAULT_MAX_SIZE = 240

internal fun ZoomableGestureImageView.createFlickGestureListener(
    onMove: (moveRatio: Float) -> Unit,
    onDismiss: (flickAnimationDuration: Long) -> Unit = {}
): FlickGestureListener {
    val callbacks = FlickCallbacks(onMove, onDismiss)

    val contentSizeProvider = ContentSizeProvider2 {
        maxOf(context!!.dip(DEFAULT_MAX_SIZE), zoomedImageHeight.toInt())
    }

    return FlickGestureListener(context!!, contentSizeProvider, callbacks).apply {
        gestureInterceptor = { scrollY ->
            val isScrollingUpwards = scrollY < 0
            val directionInt = if (isScrollingUpwards) -1 else +1
            val canPanFurther = canScrollVertically(directionInt)

            when {
                canPanFurther -> InterceptResult.INTERCEPTED
                else -> InterceptResult.IGNORED
            }
        }
    }
}

private fun Context.dip(units: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        units.toFloat(),
        resources.displayMetrics
    ).toInt()
}