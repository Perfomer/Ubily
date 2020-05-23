package com.vmedia.core.common.android.view.system

import android.app.Activity
import android.view.View
import com.vmedia.core.common.android.view.system.SystemUiColorMode.Transparent

class SystemUiColorManager(val activity: Activity) {

    var mode: SystemUiColorMode = Transparent(true)
        set(value) {
            field.disable(window)
            field = value
            field.enable(window)
        }

    private val window = activity.window

    fun init() {
        setupTranslucentSystemUi()
    }

    private fun setupTranslucentSystemUi() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

}