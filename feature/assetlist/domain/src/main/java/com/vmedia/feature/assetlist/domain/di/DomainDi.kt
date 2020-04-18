package com.vmedia.feature.assetlist.domain.di

import com.vmedia.feature.assetlist.domain.AssetListInteractor
import org.koin.dsl.module

val domainModule = module {
    single { AssetListInteractor(get()) }
}