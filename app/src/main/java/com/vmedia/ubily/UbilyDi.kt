package com.vmedia.ubily

import com.vmedia.core.di.coreModules
import com.vmedia.core.di.featureModules
import com.vmedia.core.navigation.navigationModule
import com.vmedia.core.network.datasource.MutableNetworkCredentialsProvider
import com.vmedia.core.network.datasource.NetworkCredentialsProvider
import com.vmedia.core.sync.datasource.SynchronizationDataTypeProvider
import com.vmedia.ubily.data.NetworkCredentialsHolder
import com.vmedia.ubily.data.SynchronizationDataTypeProviderImpl
import org.koin.dsl.module

internal val koinModules by lazy {
    appModules + featureModules + coreModules
}

private val appModules by lazy {
    appModule + navigationModule
}

private val appModule = module {
    single<NetworkCredentialsProvider> { get<MutableNetworkCredentialsProvider>() }
    single<MutableNetworkCredentialsProvider> { NetworkCredentialsHolder() }
    single<SynchronizationDataTypeProvider> { SynchronizationDataTypeProviderImpl() }
}