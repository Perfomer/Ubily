package com.vmedia.core.common.android.view

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.vmedia.core.common.android.R
import com.vmedia.core.common.android.util.getColorCompat
import com.vmedia.core.common.pure.util.applyBitwiseFlag

abstract class BaseActivity(
    @LayoutRes private val screenLayoutResource: Int
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTranslucentSystemUi()
        setNavigationBarDark(false)
        window.statusBarColor = getColorCompat(R.color.black_20)

        super.onCreate(savedInstanceState)
        setContentView(screenLayoutResource)
    }

    fun setNavigationBarDark(dark: Boolean) {
        val color = if (dark) R.color.black_20 else R.color.grey_light_65

        with(window) {
            navigationBarColor = getColorCompat(color)

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

            decorView.systemUiVisibility = decorView.systemUiVisibility.applyBitwiseFlag(
                flag = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR,
                apply = !dark
            )
        }
    }

    private fun setupTranslucentSystemUi() {
        val flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        window.decorView.systemUiVisibility = flags
    }

}