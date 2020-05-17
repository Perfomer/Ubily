package com.vmedia.feature.splash.presentation.di

import androidx.fragment.app.Fragment
import com.vmedia.core.navigation.BEAN_FRAGMENT_SPLASH
import com.vmedia.feature.splash.presentation.SplashFragment
import com.vmedia.feature.splash.presentation.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureSplashPresentationModule = module {
    viewModel { SplashViewModel(get()) }
    factory<Fragment>(named(BEAN_FRAGMENT_SPLASH)) { SplashFragment() }
}