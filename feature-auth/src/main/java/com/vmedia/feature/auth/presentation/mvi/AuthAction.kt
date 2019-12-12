package com.vmedia.feature.auth.presentation.mvi

import com.vmedia.feature.auth.presentation.AuthInputError

internal sealed class AuthAction {

    class LoginUpdated(val login: String) : AuthAction()

    class PasswordUpdated(val password: String) : AuthAction()

    class InputErrorsFound(val errors: List<AuthInputError>) : AuthAction()

    object AuthStarted : AuthAction()

    object AuthSucceed : AuthAction()

    object AuthFailed : AuthAction()

}