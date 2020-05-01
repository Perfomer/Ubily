package com.vmedia.ubily.presentation.base

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import com.vmedia.core.common.android.util.doOnApplyWindowInsets
import com.vmedia.core.navigation.ScreenDestination
import com.vmedia.ubily.R
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

abstract class BaseActivity(
    @LayoutRes private val screenLayoutResource: Int,
    @IdRes private val frameLayoutResource: Int,
    private val startScreen: ScreenDestination
) : AppCompatActivity() {

    private val navigatorHolder by inject<NavigatorHolder>()

    private val navigator by inject<SupportAppNavigator> {
        parametersOf(this, frameLayoutResource)
    }

    private val router by inject<Router>()


    override fun onCreate(savedInstanceState: Bundle?) {
        applyActivityWindowInsets()
        super.onCreate(savedInstanceState)
        setContentView(screenLayoutResource)

        val frameLayout = findViewById<FrameLayout>(frameLayoutResource)
        frameLayout.applyWindowInsets()

        if (savedInstanceState == null) {
            router.newRootScreen(startScreen)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() = router.exit()


    protected fun navigateTo(screen: ScreenDestination) = router.navigateTo(screen)

    private fun applyActivityWindowInsets() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                decorView.systemUiVisibility = decorView.systemUiVisibility or
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }

            statusBarColor = ContextCompat.getColor(context, R.color.black_20)
            navigationBarColor = ContextCompat.getColor(context, R.color.white_50)
        }
    }

    private fun FrameLayout.applyWindowInsets() {
        doOnApplyWindowInsets { view, insets, initialPadding ->
            view.updatePadding(
                left = initialPadding.left + insets.systemWindowInsetLeft,
                right = initialPadding.right + insets.systemWindowInsetRight
            )

            insets.replaceSystemWindowInsets(
                Rect(
                    0,
                    insets.systemWindowInsetTop,
                    0,
                    insets.systemWindowInsetBottom
                )
            )
        }
    }

}