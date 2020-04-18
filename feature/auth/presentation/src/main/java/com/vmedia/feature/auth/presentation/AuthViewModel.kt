package com.vmedia.feature.auth.presentation

import com.vmedia.core.common.mvi.MviViewModel
import com.vmedia.core.common.util.toObservable
import com.vmedia.feature.auth.domain.AuthInteractor
import com.vmedia.feature.auth.presentation.mvi.AuthAction
import com.vmedia.feature.auth.presentation.mvi.AuthAction.*
import com.vmedia.feature.auth.presentation.mvi.AuthIntent
import com.vmedia.feature.auth.presentation.mvi.AuthState
import com.vmedia.feature.auth.presentation.mvi.AuthSubscription
import io.reactivex.Observable

internal class AuthViewModel(
    private val interactor: AuthInteractor
) : MviViewModel<AuthIntent, AuthAction, AuthState, AuthSubscription>(
    initialState = AuthState()
) {

    override fun act(
        state: AuthState,
        intent: AuthIntent
    ) = when (intent) {
        is AuthIntent.UpdateLogin -> LoginUpdated(intent.login).toObservable()
        is AuthIntent.UpdatePassword -> PasswordUpdated(intent.password).toObservable()
        AuthIntent.SignIn -> /*getInputErrors(state) ?:*/ signIn(state)
    }

    override fun reduce(
        oldState: AuthState,
        action: AuthAction
    ) = when (action) {
        is LoginUpdated -> oldState.copy(login = action.login)
        is PasswordUpdated -> oldState.copy(password = action.password)
        is InputErrorsFound -> oldState.copy(inputErrors = action.errors)
        AuthStarted -> oldState.copy(isLoading = true)
        is AuthFailed, AuthSucceed -> oldState.copy(isLoading = false)
    }

    override fun publishSubscription(
        state: AuthState,
        action: AuthAction
    ) = when (action) {
        AuthSucceed -> AuthSubscription.AuthSucceed
        is AuthFailed -> AuthSubscription.AuthFailed(action.error)
        else -> super.publishSubscription(state, action)
    }


    private fun signIn(state: AuthState): Observable<AuthAction> {
        return interactor.signIn(state.login, state.password)
            .andThen(AuthSucceed.toObservable<AuthAction>())
            .startWith(AuthStarted)
            .onErrorReturn(::AuthFailed)
    }

    private fun getInputErrors(state: AuthState): Observable<InputErrorsFound>? {
        val errors = mutableListOf<AuthInputError>()

        if (state.password.length < 8) errors += AuthInputError.INCORRECT_PASSWORD
        if (!state.login.contains('@')) errors += AuthInputError.INCORRECT_LOGIN

        return if (errors.isEmpty()) null
        else Observable.just(InputErrorsFound(errors))
    }

}