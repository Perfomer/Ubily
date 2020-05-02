package com.vmedia.core.common.android.view

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.vmedia.core.common.android.R
import com.vmedia.core.common.android.util.getColorCompat
import com.vmedia.core.common.pure.util.or

abstract class BaseActivity(
    @LayoutRes private val screenLayoutResource: Int
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        applyActivityWindowInsets()
        super.onCreate(savedInstanceState)
        setContentView(screenLayoutResource)
    }

    fun setNavigationBarDark(dark: Boolean) {
        val color = if (dark) R.color.black_20 else R.color.white_50

        with(window) {
            navigationBarColor = getColorCompat(color)

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

            decorView.systemUiVisibility = decorView.systemUiVisibility.or(
                other = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR,
                exclusive = dark
            )
        }
    }

    private fun applyActivityWindowInsets() {
        with(window) {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                decorView.systemUiVisibility =
                    decorView.systemUiVisibility or
                            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }

            statusBarColor = ContextCompat.getColor(context, R.color.black_20)
            navigationBarColor = ContextCompat.getColor(context, R.color.white_50)
        }
    }

}