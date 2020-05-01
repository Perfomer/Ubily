package com.vmedia.feature.auth.presentation

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import com.vmedia.core.common.android.mvi.MviFragment
import com.vmedia.core.common.android.util.*
import com.vmedia.core.navigation.navigator.auth.AuthNavigator
import com.vmedia.feature.auth.presentation.mvi.AuthIntent
import com.vmedia.feature.auth.presentation.mvi.AuthState
import com.vmedia.feature.auth.presentation.mvi.AuthSubscription
import com.vmedia.feature.auth.presentation.mvi.AuthSubscription.AuthFailed
import com.vmedia.feature.auth.presentation.mvi.AuthSubscription.AuthSucceed
import kotlinx.android.synthetic.main.auth_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

internal class AuthFragment : MviFragment<AuthIntent, AuthState, AuthSubscription>(
    layoutResource = R.layout.auth_fragment
) {

    private val navigator: AuthNavigator
        get() = activity as AuthNavigator

    override fun provideViewModel() = getViewModel<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth_signin.setOnClickListener(::signIn)
        auth_login.addTextChangedListener { postIntent(AuthIntent.UpdateLogin(it)) }
        auth_password.addTextChangedListener { postIntent(AuthIntent.UpdatePassword(it)) }
        auth_password.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE, ::signIn)
    }

    override fun render(state: AuthState) {
        auth_login.diffedValue = state.login
        auth_password.diffedValue = state.password

        auth_loading.isVisible = state.isLoading
        auth_signin.isEnabled = !state.isLoading

        if (state.inputErrors.isNotEmpty()) {
            toast("there is input error") //todo input errors
        }
    }

    override fun onSubscriptionReceived(subscription: AuthSubscription) {
        when (subscription) {
            AuthSucceed -> navigator.onAuthSucceed()
            is AuthFailed -> toast("there is AUTH error") //todo
        }
    }

    private fun signIn() {
        hideKeyboard()
        postIntent(AuthIntent.SignIn)
    }

}