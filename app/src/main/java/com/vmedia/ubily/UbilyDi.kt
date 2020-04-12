package com.vmedia.ubily

import com.vmedia.core.data.dataModules
import com.vmedia.core.navigation.navigationModule
import com.vmedia.core.network.datasource.MutableNetworkCredentialsProvider
import com.vmedia.core.network.datasource.NetworkCredentialsProvider
import com.vmedia.core.network.networkModules
import com.vmedia.core.sync.datasource.SynchronizationDataTypeProvider
import com.vmedia.core.sync.syncModules
import com.vmedia.feature.auth.api.featureAuthModules
import com.vmedia.feature.feed.feedModule
import com.vmedia.feature.splash.splashModule
import com.vmedia.feature.sync.api.featureSyncModules
import org.koin.dsl.module

internal val koinModules by lazy {
    appModules + featureModules + featureModulesOld + coreModules
}


private val appModules by lazy {
    appModule + navigationModule
}

private val coreModules by lazy {
    dataModules + networkModules + syncModules
}

private val featureModules by lazy {
    featureAuthModules + featureSyncModules
}

private val featureModulesOld = listOf(
    splashModule,
    feedModule
)

private val appModule = module {
    single<NetworkCredentialsProvider> { get<MutableNetworkCredentialsProvider>() }
    single<MutableNetworkCredentialsProvider> { NetworkCredentialsHolder() }
    single<SynchronizationDataTypeProvider> { SynchronizationDataTypeProviderImpl() }
}