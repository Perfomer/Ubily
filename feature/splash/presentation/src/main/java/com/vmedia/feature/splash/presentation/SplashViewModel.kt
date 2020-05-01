package com.vmedia.feature.splash.presentation

import com.vmedia.core.common.android.mvi.MviViewModel
import com.vmedia.feature.splash.domain.SplashInteractor
import com.vmedia.feature.splash.presentation.mvi.SplashAction
import com.vmedia.feature.splash.presentation.mvi.SplashAction.*
import com.vmedia.feature.splash.presentation.mvi.SplashIntent
import com.vmedia.feature.splash.presentation.mvi.SplashIntent.Initialize
import com.vmedia.feature.splash.presentation.mvi.SplashState
import com.vmedia.feature.splash.presentation.mvi.SplashSubscription

internal class SplashViewModel(
    private val interactor: SplashInteractor
) : MviViewModel<SplashIntent, SplashAction, SplashState, SplashSubscription>(
    initialState = SplashState()
) {

    override fun act(
        state: SplashState,
        intent: SplashIntent
    ) = when (intent) {
        Initialize -> interactor.initialize()
            .toObservable()
            .asFlowSource(Initialize::class)
            .map<SplashAction>(::InitializingSucceed)
            .startWith(InitializingStarted)
            .onErrorReturn(::InitializingFailed)
    }

    override fun reduce(
        oldState: SplashState,
        action: SplashAction
    ) = when (action) {
        InitializingStarted -> oldState.copy(isLoading = true, error = null)
        is InitializingSucceed -> oldState.copy(isLoading = false)
        is InitializingFailed -> oldState.copy(isLoading = false, error = action.error)
    }

    override fun publishSubscription(
        state: SplashState,
        action: SplashAction
    ) = when (action) {
        is InitializingSucceed -> SplashSubscription.InitializingSucceed(action.result)
        else -> super.publishSubscription(state, action)
    }

}