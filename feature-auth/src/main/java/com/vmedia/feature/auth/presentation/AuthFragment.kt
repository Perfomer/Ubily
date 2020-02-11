package com.vmedia.feature.auth.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.vmedia.core.common.mvi.MviFragment
import com.vmedia.core.common.util.addTextChangedListener
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.toast
import com.vmedia.feature.auth.R
import com.vmedia.feature.auth.presentation.mvi.AuthIntent
import com.vmedia.feature.auth.presentation.mvi.AuthState
import com.vmedia.feature.auth.presentation.mvi.AuthSubscription
import kotlinx.android.synthetic.main.auth_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

internal class AuthFragment : MviFragment<AuthIntent, AuthState, AuthSubscription>(
    layoutResource = R.layout.auth_fragment
) {

    override fun provideViewModel() = getViewModel<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth_signin.setOnClickListener { postIntent(AuthIntent.SignIn) }
        auth_login.addTextChangedListener { postIntent(AuthIntent.UpdateLogin(it)) }
        auth_password.addTextChangedListener { postIntent(AuthIntent.UpdatePassword(it)) }
    }

    override fun render(state: AuthState) {
        auth_login.diffedValue = state.login
        auth_password.diffedValue = state.password

        auth_progressbar.isVisible = state.isLoading
        auth_signin.isEnabled = !state.isLoading

        if (state.inputErrors.isNotEmpty()) {
            toast("there is input error") //todo input errors
        }
    }

    override fun onSubscriptionReceived(subscription: AuthSubscription) {
        when (subscription) {
            AuthSubscription.AuthSucceed -> goBack()
            AuthSubscription.AuthFailed -> toast("there is AUTH error") //todo
        }
    }

}