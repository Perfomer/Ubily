package com.vmedia.feature.splash.domain

import com.vmedia.feature.splash.domain.entity.InitializationResult
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles

class SplashInteractor(
    private val repository: SplashRepository,
) {

    fun initialize(): Single<InitializationResult> {
        return repository.syncNetworkCredentials()
            .andThen(
                Singles.zip(
                    repository.isUserAuthorized(),
                    repository.isSynchronizationSucceedAtLeastOnce(),
                    repository.isOnboardingAlreadyShown()
                ) { isUserAuthorized, isSynchronizationSucceedAtLeastOnce, isOnboardingAlreadyShown ->
                    InitializationResult(
                        isUserAuthorized = isUserAuthorized,
                        isSynchronizationSucceedAtLeastOnce = isSynchronizationSucceedAtLeastOnce,
                        isOnboardingAlreadyShown = isOnboardingAlreadyShown
                    )
                }
            )
    }

}
