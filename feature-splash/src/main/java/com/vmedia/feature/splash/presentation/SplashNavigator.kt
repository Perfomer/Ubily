package com.vmedia.feature.splash.presentation

interface SplashNavigator {

    fun onInitialized(
        isUserAuthorized: Boolean,
        isUserDataSynchronized: Boolean,
        onboardingAlreadyShown: Boolean
    )

}