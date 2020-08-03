package com.vmedia.core.data.datasource.impl

import android.content.SharedPreferences
import com.vmedia.core.common.android.util.preferences.value
import com.vmedia.core.data.datasource.PublisherDataSource
import io.reactivex.Single

internal class PublisherDataSourceImpl(
    private val preferences: SharedPreferences
): PublisherDataSource {

    private var isOnboardingAlreadyShown: Boolean by preferences.value(KEY_ONBOARDING, false)

    override fun isOnboardingAlreadyShown(): Single<Boolean> {
        return Single.fromCallable { isOnboardingAlreadyShown }
    }

    private companion object {

        private const val KEY_ONBOARDING = "onboarding_shown"

    }

}