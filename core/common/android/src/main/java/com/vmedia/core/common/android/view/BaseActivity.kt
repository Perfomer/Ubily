package com.vmedia.core.common.android.view

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.vmedia.core.common.android.R
import com.vmedia.core.common.android.util.getColorCompat

abstract class BaseActivity(
    @LayoutRes private val screenLayoutResource: Int
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        applyActivityWindowInsets()
        super.onCreate(savedInstanceState)
        setContentView(screenLayoutResource)
    }

    fun setNavigationBarDark(dark: Boolean) {
        with(window) {
            if (dark) {
                navigationBarColor = getColorCompat(R.color.black_20)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    decorView.systemUiVisibility =
                        decorView.systemUiVisibility xor View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }
            } else {
                navigationBarColor = getColorCompat(R.color.white_50)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    decorView.systemUiVisibility =
                        decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                }
            }
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