package com.vmedia.feature.auth.domain.di

import com.vmedia.feature.auth.domain.AuthInteractor
import org.koin.dsl.module

val featureAuthDomainModule = module {
    single { AuthInteractor(get()) }
}