package com.vmedia.core.navigation

import android.graphics.Rect
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.view.updatePadding
import com.vmedia.core.common.android.util.doOnApplyWindowInsets
import com.vmedia.core.common.android.view.BaseActivity
import com.vmedia.core.navigation.cicerone.UbilyRouter
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder

abstract class NavigationActivity(
    screenLayoutResource: Int,
    private val frameLayoutResource: Int,
    private val startScreen: ScreenDestination
) : BaseActivity(screenLayoutResource) {

    private val navigatorHolder by inject<NavigatorHolder>()

    private val navigator by inject<Navigator> {
        parametersOf(this, frameLayoutResource)
    }

    private val router by inject<UbilyRouter>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    override fun onBackPressed() {
        router.exit()
    }

    protected fun navigateTo(screen: ScreenDestination) {
        router.forward(screen)
    }

    protected fun addOver(screen: ScreenDestination) {
        router.addOver(screen)
    }

    protected fun remove(screen: ScreenDestination) {
        router.remove(screen)
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