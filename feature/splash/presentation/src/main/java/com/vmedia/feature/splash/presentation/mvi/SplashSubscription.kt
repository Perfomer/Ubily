package com.vmedia.feature.splash.presentation.mvi

import com.vmedia.feature.splash.domain.entity.InitializationResult

internal sealed class SplashSubscription {

    class InitializingSucceed(val result: InitializationResult) : SplashSubscription()

}