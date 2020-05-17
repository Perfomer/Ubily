package com.vmedia.feature.assetlist.presentation.di

import androidx.fragment.app.Fragment
import com.vmedia.core.navigation.BEAN_FRAGMENT_ASSETLIST
import com.vmedia.feature.assetlist.presentation.AssetListFragment
import com.vmedia.feature.assetlist.presentation.AssetListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureAssetListPresentationModule = module {
    viewModel { AssetListViewModel(get()) }
    factory<Fragment>(named(BEAN_FRAGMENT_ASSETLIST)) { AssetListFragment() }
}