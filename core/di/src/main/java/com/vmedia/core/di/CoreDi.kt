package com.vmedia.core.di

import com.vmedia.core.data.coreDataDatabaseModule
import com.vmedia.core.data.coreDataPreferencesModule
import com.vmedia.core.network.coreNetworkModule
import com.vmedia.core.network.coreNetworkRetrofitModule
import com.vmedia.core.network.coreNetworkUtilsModule
import com.vmedia.core.sync.*

val coreModules
    get() = listOf(
        coreDataModules,
        coreNetworkModules,
        coreSyncModules
    ).flatten()

private val coreDataModules = listOf(
    coreDataPreferencesModule, coreDataDatabaseModule
)

private val coreNetworkModules by lazy {
    listOf(coreNetworkModule, coreNetworkUtilsModule, coreNetworkRetrofitModule)
}

private val coreSyncModules by lazy {
    listOf(
        coreSyncModule,
        coreSyncNotificationModule,
        coreSyncSynchronizerModule,
        coreSyncEventExtractorModule,
        coreSyncMapperModule,
        coreSyncFilterModule,
        coreSyncProviderModule
    )
}
