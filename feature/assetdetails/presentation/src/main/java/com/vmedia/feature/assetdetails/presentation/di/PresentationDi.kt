package com.vmedia.feature.assetdetails.presentation.di

import androidx.fragment.app.Fragment
import com.vmedia.feature.assetdetails.api.BEAN_FRAGMENT_ASSETDETAILS
import com.vmedia.feature.assetdetails.presentation.AssetDetailsFragment
import com.vmedia.feature.assetdetails.presentation.AssetDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureAssetDetailsPresentationModule = module {
    viewModel { (assetId: Long) -> AssetDetailsViewModel(assetId, get()) }

    factory<Fragment>(named(BEAN_FRAGMENT_ASSETDETAILS)) { (assetId: Long) ->
        AssetDetailsFragment.newInstance(assetId)
    }
}