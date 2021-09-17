package com.vmedia.core.common.android.util

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.ResourcesCompat

val Context.notificationManager: NotificationManagerCompat
    get() = NotificationManagerCompat.from(this)

@ColorInt
fun Context.getColorCompat(@ColorRes id: Int): Int {
    return ResourcesCompat.getColor(resources, id, null)
}

fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable {
    return ResourcesCompat.getDrawable(resources, id, null)!!
}

fun Context.getFontCompat(@FontRes id: Int): Typeface {
    return ResourcesCompat.getFont(this, id)!!
}
