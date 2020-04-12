package com.vmedia.feature.splash.presentation.mvi

import com.vmedia.feature.splash.domain.entity.InitializationResult

internal sealed class SplashAction {

    object InitializingStarted : SplashAction()

    class InitializingSucceed(val result: InitializationResult) : SplashAction()

    class InitializingFailed(val error: Throwable) : SplashAction()

}