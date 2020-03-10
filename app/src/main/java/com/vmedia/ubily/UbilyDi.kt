package com.vmedia.ubily

import com.vmedia.core.data.dataModules
import com.vmedia.core.network.datasource.MutableNetworkCredentialsProvider
import com.vmedia.core.network.datasource.NetworkCredentialsProvider
import com.vmedia.core.network.networkModules
import com.vmedia.core.sync.syncModule
import com.vmedia.feature.auth.authModule
import org.koin.dsl.module.module

internal val koinModules by lazy {
    featureModules + dataModules + networkModules + syncModule + appModule
}

internal val featureModules = listOf(
    authModule
)

internal val appModule = module {
    single { NetworkCredentialsSynchronizer(get(), get(), get()) }
    single<NetworkCredentialsProvider> { get<MutableNetworkCredentialsProvider>() }
    single<MutableNetworkCredentialsProvider> { NetworkCredentialsHolder() }
}