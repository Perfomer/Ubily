package com.vmedia.feature.assetdetails.domain.di

import com.vmedia.feature.assetdetails.domain.AssetDetailsInteractor
import org.koin.dsl.module

val domainModule = module {
    single { AssetDetailsInteractor(get()) }
}