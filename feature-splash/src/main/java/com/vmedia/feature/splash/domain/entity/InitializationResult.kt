package com.vmedia.feature.splash.domain.entity

internal class InitializationResult(
    val isUserAuthorized: Boolean,
    val synchronizationSucceedAtLeastOnce: Boolean
)