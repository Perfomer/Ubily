package com.vmedia.feature.splash.domain.entity

class InitializationResult(
    val isUserAuthorized: Boolean,
    val isSynchronizationSucceedAtLeastOnce: Boolean,
    val isOnboardingAlreadyShown: Boolean
)
