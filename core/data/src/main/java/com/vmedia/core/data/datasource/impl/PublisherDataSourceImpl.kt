package com.vmedia.core.data.datasource.impl

import android.content.SharedPreferences
import com.vmedia.core.data.datasource.PublisherDataSource
import io.reactivex.Single

internal class PublisherDataSourceImpl(
    private val preferences: SharedPreferences
): PublisherDataSource {

    override fun isOnboardingAlreadyShown(): Single<Boolean> {
        return Single.fromCallable { preferences.getBoolean(KEY_ONBOARDING, false) }
    }

    private companion object {

        private const val KEY_ONBOARDING = "onboarding_shown"

    }

}