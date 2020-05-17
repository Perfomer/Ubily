package com.vmedia.feature.splash.data.di

import com.vmedia.feature.splash.data.NetworkCredentialsDataSource
import com.vmedia.feature.splash.data.NetworkCredentialsDataSourceImpl
import com.vmedia.feature.splash.data.SplashRepositoryImpl
import com.vmedia.feature.splash.domain.SplashRepository
import org.koin.dsl.module

val featureSplashDataModule = module {
    single<NetworkCredentialsDataSource> {
        NetworkCredentialsDataSourceImpl(
            networkCredentialsProvider = get(),
            credentialsDataSource = get(),
            databaseDataSource = get()
        )
    }

    single<SplashRepository> { SplashRepositoryImpl(get(), get(), get()) }
}