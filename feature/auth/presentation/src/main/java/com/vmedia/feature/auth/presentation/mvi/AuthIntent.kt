package com.vmedia.feature.auth.presentation.mvi

internal sealed class AuthIntent {

    class UpdateLogin(val login: String) : AuthIntent()

    class UpdatePassword(val password: String) : AuthIntent()

    object SignIn : AuthIntent()

}