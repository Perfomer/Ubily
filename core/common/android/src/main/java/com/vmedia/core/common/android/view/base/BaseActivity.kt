package com.vmedia.core.common.android.view.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.vmedia.core.common.android.view.system.SystemUiColorManager
import com.vmedia.core.common.android.view.system.SystemUiColorMode

abstract class BaseActivity(
    @LayoutRes private val screenLayoutResource: Int
) : AppCompatActivity() {

    private val systemUiColorManager by lazy { SystemUiColorManager(this) }

    var systemUiColorMode: SystemUiColorMode
        get() = systemUiColorManager.mode
        set(value) {
            systemUiColorManager.mode = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        systemUiColorManager.init()
        super.onCreate(savedInstanceState)
        setContentView(screenLayoutResource)
    }

}
