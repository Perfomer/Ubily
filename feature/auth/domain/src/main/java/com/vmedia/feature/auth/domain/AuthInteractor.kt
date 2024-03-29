package com.vmedia.feature.auth.domain

import com.vmedia.core.common.pure.obj.creds.Credentials
import io.reactivex.Completable

class AuthInteractor(
    private val repository: AuthRepository
) {

    fun signIn(login: String, password: String): Completable {
        return repository.signIn(login, password)
            .andThen(repository.extractToken())
            .map { Credentials(login, password, it) }
            .flatMapCompletable(repository::saveCredentials)
    }

}