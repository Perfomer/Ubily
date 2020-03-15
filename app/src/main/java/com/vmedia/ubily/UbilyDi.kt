package com.vmedia.ubily

import com.vmedia.core.data.dataModules
import com.vmedia.core.network.datasource.MutableNetworkCredentialsProvider
import com.vmedia.core.network.datasource.NetworkCredentialsProvider
import com.vmedia.core.network.networkModules
import com.vmedia.core.sync.syncModules
import com.vmedia.feature.auth.authModule
import org.koin.dsl.module.module

internal val koinModules by lazy {
    featureModules + dataModules + networkModules + syncModules + appModule
}

private val featureModules = listOf(
    authModule
)

private val appModule = module {
    single { NetworkCredentialsSynchronizer(get(), get(), get()) }
    single<NetworkCredentialsProvider> { get<MutableNetworkCredentialsProvider>() }
    single<MutableNetworkCredentialsProvider> { NetworkCredentialsHolder() }
}