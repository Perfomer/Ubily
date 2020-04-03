package com.vmedia.ubily.presentation

import com.vmedia.feature.auth.presentation.AuthNavigator
import com.vmedia.feature.splash.presentation.SplashNavigator
import com.vmedia.ubily.R
import com.vmedia.ubily.presentation.base.BaseActivity
import com.vmedia.ubily.presentation.navigation.ScreenDestination

class MainActivity : BaseActivity(
    screenLayoutResource = R.layout.activity_main,
    frameLayoutResource = R.id.nav_host_fragment,
    startScreen = ScreenDestination.Splash
), SplashNavigator, AuthNavigator {

    override fun onInitialized(
        isUserAuthorized: Boolean,
        isUserDataSynchronized: Boolean,
        onboardingAlreadyShown: Boolean
    ) {
        if (isUserAuthorized) {
            if (isUserDataSynchronized) {
                navigateTo(ScreenDestination.Feed)
            } else {
                if (onboardingAlreadyShown) navigateTo(ScreenDestination.Sync)
                else navigateTo(ScreenDestination.Onboarding)
            }
        } else {
            navigateTo(ScreenDestination.Auth)
        }
    }

    override fun onAuthSucceed() {
        navigateTo(ScreenDestination.Splash)
    }

}