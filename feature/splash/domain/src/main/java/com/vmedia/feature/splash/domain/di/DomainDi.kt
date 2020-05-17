package com.vmedia.feature.splash.domain.di

import com.vmedia.feature.splash.domain.SplashInteractor
import org.koin.dsl.module

val featureSplashDomainModule = module {
    single { SplashInteractor(get()) }
}