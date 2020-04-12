package com.vmedia.ubily

import com.vmedia.core.data.dataModules
import com.vmedia.core.navigation.navigationModule
import com.vmedia.core.network.datasource.MutableNetworkCredentialsProvider
import com.vmedia.core.network.datasource.NetworkCredentialsProvider
import com.vmedia.core.network.networkModules
import com.vmedia.core.sync.datasource.SynchronizationDataTypeProvider
import com.vmedia.core.sync.syncModules
import com.vmedia.feature.auth.api.authModules
import com.vmedia.feature.feed.feedModule
import com.vmedia.feature.splash.splashModule
import com.vmedia.feature.sync.syncModule
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
    authModules
}

private val featureModulesOld = listOf(
    splashModule,
    syncModule,
    feedModule
)

private val appModule = module {
    single<NetworkCredentialsProvider> { get<MutableNetworkCredentialsProvider>() }
    single<MutableNetworkCredentialsProvider> { NetworkCredentialsHolder() }
    single<SynchronizationDataTypeProvider> { SynchronizationDataTypeProviderImpl() }
}