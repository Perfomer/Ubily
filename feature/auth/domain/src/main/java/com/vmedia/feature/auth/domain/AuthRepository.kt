package com.vmedia.feature.auth.domain

import com.vmedia.core.common.obj.creds.Credentials
import com.vmedia.core.common.obj.creds.Token
import io.reactivex.Completable
import io.reactivex.Single

interface AuthRepository {

    fun extractToken(): Single<Token>

    fun signIn(login: String, password: String): Completable

    fun saveCredentials(credentials: Credentials): Completable

}