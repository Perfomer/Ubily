package com.vmedia.core.navigation.navigator.splash

interface SplashNavigator {

    fun onInitialized(
        isUserAuthorized: Boolean,
        isUserDataSynchronized: Boolean,
        onboardingAlreadyShown: Boolean
    )

}