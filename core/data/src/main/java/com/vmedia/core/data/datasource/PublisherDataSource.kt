package com.vmedia.core.data.datasource

import io.reactivex.Single

interface PublisherDataSource {

    fun isOnboardingAlreadyShown(): Single<Boolean>

}