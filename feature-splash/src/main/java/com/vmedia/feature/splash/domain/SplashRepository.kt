package com.vmedia.feature.splash.domain

import io.reactivex.Completable
import io.reactivex.Single

internal interface SplashRepository {

    fun syncNetworkCredentials(): Completable

    fun isUserAuthorized(): Single<Boolean>

    fun isSynchronizationSucceedAtLeastOnce(): Single<Boolean>

}