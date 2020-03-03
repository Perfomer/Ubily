package com.vmedia.ubily

import com.vmedia.core.data.dataModules
import com.vmedia.core.network.networkModules
import com.vmedia.feature.auth.authModule
import org.koin.dsl.module.module

internal val koinModules by lazy {
    featureModules + dataModules + networkModules + appModule
}

internal val featureModules = listOf(
    authModule
)

internal val appModule = module {
    single { NetworkCredentialsSynchronizer(get(), get(), get()) }
}