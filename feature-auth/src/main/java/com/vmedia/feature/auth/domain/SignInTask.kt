package com.vmedia.feature.auth.domain

import io.reactivex.Completable

@FunctionalInterface
internal interface SignInTask {

    fun signIn(login: String, password: String): Completable

}