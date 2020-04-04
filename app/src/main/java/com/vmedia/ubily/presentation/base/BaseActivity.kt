package com.vmedia.ubily.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.vmedia.ubily.presentation.navigation.ScreenDestination
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

    private val navigator by inject<SupportAppNavigator> { parametersOf(this, frameLayoutResource) }

    private val router by inject<Router>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        setContentView(screenLayoutResource)

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

}