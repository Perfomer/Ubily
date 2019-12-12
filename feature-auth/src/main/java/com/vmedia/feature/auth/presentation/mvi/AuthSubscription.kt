package com.vmedia.feature.auth.presentation.mvi

internal sealed class AuthSubscription {

    object AuthSucceed : AuthSubscription()

    object AuthFailed : AuthSubscription()

}