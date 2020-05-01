package com.vmedia.ubily

import com.vmedia.core.data.dataModules
import com.vmedia.core.navigation.navigationModule
import com.vmedia.core.network.datasource.MutableNetworkCredentialsProvider
import com.vmedia.core.network.datasource.NetworkCredentialsProvider
import com.vmedia.core.network.networkModules
import com.vmedia.core.sync.datasource.SynchronizationDataTypeProvider
import com.vmedia.core.sync.syncModules
import com.vmedia.feature.assetdetails.api.featureAssetDetailsModules
import com.vmedia.feature.assetlist.api.featureAssetListModules
import com.vmedia.feature.auth.api.featureAuthModules
import com.vmedia.feature.eventdetails.api.featureEventDetailsModules
import com.vmedia.feature.feed.api.featureFeedModules
import com.vmedia.feature.gallery.api.featureGalleryModules
import com.vmedia.feature.splash.api.featureSplashModules
import com.vmedia.feature.sync.api.featureSyncModules
import org.koin.dsl.module

internal val koinModules by lazy {
    appModules + featureModules + coreModules
}


private val appModules by lazy {
    appModule + navigationModule
}

private val coreModules
    get() = listOf(
        dataModules,
        networkModules,
        syncModules
    ).flatten()


private val featureModules
    get() = listOf(
        featureAuthModules,
        featureSyncModules,
        featureSplashModules,
        featureFeedModules,
        featureEventDetailsModules,
        featureAssetDetailsModules,
        featureAssetListModules,
        featureGalleryModules
    ).flatten()


private val appModule = module {
    single<NetworkCredentialsProvider> { get<MutableNetworkCredentialsProvider>() }
    single<MutableNetworkCredentialsProvider> { NetworkCredentialsHolder() }
    single<SynchronizationDataTypeProvider> { SynchronizationDataTypeProviderImpl() }
}