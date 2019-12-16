package com.vmedia.feature.auth.domain

import com.vmedia.core.data.internal.Credentials
import io.reactivex.Completable
import io.reactivex.Single

interface AuthRepository {

    fun extractToken(): Single<String>

    fun signIn(login: String, password: String): Completable

    fun saveCredentials(credentials: Credentials): Completable

}