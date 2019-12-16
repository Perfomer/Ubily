package com.vmedia.core.data.internal

import com.vmedia.core.data.datasource.PreferencesDataSource

internal class TokenProvider(
    private val preferencesDataSource: PreferencesDataSource
) {

    val token: String
        get() = preferencesDataSource.getCredentials().blockingGet().token

}