package com.vmedia.ubily.presentation

import com.vmedia.feature.auth.presentation.AuthNavigator
import com.vmedia.feature.splash.presentation.SplashNavigator
import com.vmedia.feature.sync.presentation.SyncNavigator
import com.vmedia.feature.sync.presentation.SyncScreenMode
import com.vmedia.ubily.R
import com.vmedia.ubily.presentation.base.BaseActivity
import com.vmedia.ubily.presentation.navigation.ScreenDestination

class MainActivity : BaseActivity(
    screenLayoutResource = R.layout.activity_main,
    frameLayoutResource = R.id.nav_host_fragment,
    startScreen = ScreenDestination.Splash
), SplashNavigator, SyncNavigator, AuthNavigator {

    private var isUserAuthorized: Boolean = false
    private var isUserDataSynchronized: Boolean = false
    private var onboardingAlreadyShown: Boolean = false


    override fun onInitialized(
        isUserAuthorized: Boolean,
        isUserDataSynchronized: Boolean,
        onboardingAlreadyShown: Boolean
    ) {
        this.isUserAuthorized = isUserAuthorized
        this.isUserDataSynchronized = isUserDataSynchronized
        this.onboardingAlreadyShown = onboardingAlreadyShown

        navigateThroughStartGraph()
    }

    override fun onSynchronizationSucceed() {
        isUserDataSynchronized = true
        navigateThroughStartGraph()
    }

    override fun onAuthSucceed() {
        isUserAuthorized = true
        navigateThroughStartGraph()
    }

    private fun navigateThroughStartGraph() {
        if (isUserAuthorized) {
            if (isUserDataSynchronized) {
                navigateTo(ScreenDestination.Feed)
            } else {
//                if (onboardingAlreadyShown) {
                    navigateTo(ScreenDestination.Sync(SyncScreenMode.INITIAL))
//                } else {
//                    navigateTo(ScreenDestination.Onboarding)
//                }
            }
        } else {
            navigateTo(ScreenDestination.Auth)
        }
    }

}