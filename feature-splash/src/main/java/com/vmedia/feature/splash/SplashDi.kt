package com.vmedia.feature.splash

import androidx.fragment.app.Fragment
import com.vmedia.feature.splash.data.NetworkCredentialsDataSource
import com.vmedia.feature.splash.data.NetworkCredentialsDataSourceImpl
import com.vmedia.feature.splash.data.SplashRepositoryImpl
import com.vmedia.feature.splash.domain.SplashInteractor
import com.vmedia.feature.splash.domain.SplashRepository
import com.vmedia.feature.splash.presentation.SplashFragment
import com.vmedia.feature.splash.presentation.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BEAN_FRAGMENT_SPLASH = "SplashFragment"

val splashModule = module {
    single<NetworkCredentialsDataSource> {
        NetworkCredentialsDataSourceImpl(
            networkCredentialsProvider = get(),
            credentialsDataSource = get(),
            databaseDataSource = get()
        )
    }
    single<SplashRepository> { SplashRepositoryImpl(get(), get()) }
    single { SplashInteractor(get()) }
    viewModel { SplashViewModel(get()) }
    factory<Fragment>(named(BEAN_FRAGMENT_SPLASH)) { SplashFragment() }
}