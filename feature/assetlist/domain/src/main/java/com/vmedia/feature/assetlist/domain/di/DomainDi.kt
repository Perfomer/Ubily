package com.vmedia.feature.assetlist.domain.di

import com.vmedia.feature.assetlist.domain.AssetListInteractor
import org.koin.dsl.module

val featureAssetListDomainModule = module {
    single { AssetListInteractor(get()) }
}