package com.vmedia.feature.splash.api

const val BEAN_FRAGMENT_SPLASH = "SplashFragment"

interface SplashNavigator {

    fun onInitialized(
        isUserAuthorized: Boolean,
        isUserDataSynchronized: Boolean,
        isOnboardingAlreadyShown: Boolean
    )

}
