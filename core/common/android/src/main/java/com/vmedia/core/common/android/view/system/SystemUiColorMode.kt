package com.vmedia.core.common.android.view.system

import android.os.Build
import android.view.View
import android.view.Window
import com.vmedia.core.common.android.R
import com.vmedia.core.common.android.util.clearSystemUiVisibilityFlag
import com.vmedia.core.common.android.util.getColorCompat
import com.vmedia.core.common.android.util.setSystemUiVisibilityFlag

sealed class SystemUiColorMode {

    abstract fun enable(window: Window)
    open fun disable(window: Window) = Unit

    object Light : SystemUiColorMode() {
        override fun enable(window: Window) = with(window) {
            navigationBarColor = context.getColorCompat(R.color.grey_light_65)
            statusBarColor = context.getColorCompat(R.color.black_20)
            setLightNavigationBar(true)
        }
    }

    object Dark : SystemUiColorMode() {
        override fun enable(window: Window) = with(window) {
            navigationBarColor = context.getColorCompat(R.color.black_20)
            statusBarColor = context.getColorCompat(R.color.black_20)
            setLightNavigationBar(false)
        }
    }

    class Transparent(private val light: Boolean) : SystemUiColorMode() {
        override fun enable(window: Window) = with(window) {
            val color = context.getColorCompat(R.color.transparent)

            navigationBarColor = color
            statusBarColor = color

            setLightNavigationBar(!light) // wtf? don't know, but it works. Android...
        }
    }

    protected companion object {

        protected fun Window.setLightNavigationBar(light: Boolean) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

            if (light) setSystemUiVisibilityFlag(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
            else clearSystemUiVisibilityFlag(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        }

    }

}