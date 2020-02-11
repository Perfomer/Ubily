package com.vmedia.core.data.internal

import com.vmedia.core.data.datasource.CredentialsDataSource

internal class TokenProvider(
    private val credentialsDataSource: CredentialsDataSource
) {

    val token: String
        get() = credentialsDataSource.getCredentials().blockingGet().token

}