package com.vmedia.feature.auth.presentation.mvi

internal sealed class AuthSubscription {

    object AuthSucceed : AuthSubscription()

    class AuthFailed(val error: Throwable) : AuthSubscription()

}