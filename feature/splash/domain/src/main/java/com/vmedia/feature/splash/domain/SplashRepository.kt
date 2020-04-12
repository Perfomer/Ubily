package com.vmedia.feature.splash.domain

import io.reactivex.Completable
import io.reactivex.Single

interface SplashRepository {

    fun syncNetworkCredentials(): Completable

    fun isUserAuthorized(): Single<Boolean>

    fun isSynchronizationSucceedAtLeastOnce(): Single<Boolean>

    fun isOnboardingAlreadyShown(): Single<Boolean>

}