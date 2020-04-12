package com.vmedia.feature.splash.data

import io.reactivex.Completable
import io.reactivex.Single

internal interface NetworkCredentialsDataSource {

    fun synchronizeCredentials(): Completable

    fun hasCredentials(): Single<Boolean>

}