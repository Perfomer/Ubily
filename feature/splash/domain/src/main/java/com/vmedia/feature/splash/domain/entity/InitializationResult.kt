package com.vmedia.feature.splash.domain.entity

class InitializationResult(
    val isUserAuthorized: Boolean,
    val synchronizationSucceedAtLeastOnce: Boolean,
    val onboardingAlreadyShown: Boolean
)