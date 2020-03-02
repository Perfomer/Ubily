package com.vmedia.feature.auth.domain

import com.vmedia.core.network.obj.Credentials
import com.vmedia.core.network.obj.Token
import io.reactivex.Completable
import io.reactivex.Single

interface AuthRepository {

    fun extractToken(): Single<Token>

    fun signIn(login: String, password: String): Completable

    fun saveCredentials(credentials: Credentials): Completable

}