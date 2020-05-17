package com.vmedia.feature.assetdetails.domain.di

import com.vmedia.feature.assetdetails.domain.AssetDetailsInteractor
import org.koin.dsl.module

val featureAssetDetailsDomainModule = module {
    single { AssetDetailsInteractor(get()) }
}