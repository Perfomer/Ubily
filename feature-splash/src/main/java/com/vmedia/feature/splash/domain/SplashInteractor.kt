package com.vmedia.feature.splash.domain

import com.vmedia.feature.splash.domain.entity.InitializationResult
import io.reactivex.Single
import io.reactivex.functions.BiFunction

internal class SplashInteractor(
    private val repository: SplashRepository
) {

    fun initialize(): Single<InitializationResult> {
        return Single.zip(
            repository.isUserAuthorized(),
            repository.isSynchronizationSucceedAtLeastOnce(),
            BiFunction { isUserAuthorized, synchronizationSucceedAtLeastOnce ->
                InitializationResult(
                    isUserAuthorized = isUserAuthorized,
                    synchronizationSucceedAtLeastOnce = synchronizationSucceedAtLeastOnce
                )
            }
        )
    }

}