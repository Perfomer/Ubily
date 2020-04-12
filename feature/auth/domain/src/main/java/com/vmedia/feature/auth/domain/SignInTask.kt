package com.vmedia.feature.auth.domain

import io.reactivex.Completable

@FunctionalInterface
interface SignInTask {

    fun signIn(login: String, password: String): Completable

}