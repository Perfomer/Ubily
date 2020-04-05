package com.vmedia.feature.splash.domain

import com.vmedia.feature.splash.domain.entity.InitializationResult
import io.reactivex.Single
import io.reactivex.functions.Function3

internal class SplashInteractor(
    private val repository: SplashRepository
) {

    fun initialize(): Single<InitializationResult> {
        return repository.syncNetworkCredentials()
            .andThen(Single.zip(
                repository.isUserAuthorized(),
                repository.isSynchronizationSucceedAtLeastOnce(),
                repository.isOnboardingAlreadyShown(),
                Function3 { isUserAuthorized, synchronizationSucceedAtLeastOnce, onboardingAlreadyShown ->
                    InitializationResult(
                        isUserAuthorized = isUserAuthorized,
                        synchronizationSucceedAtLeastOnce = synchronizationSucceedAtLeastOnce,
                        onboardingAlreadyShown = onboardingAlreadyShown
                    )
                }
            ))
    }

}