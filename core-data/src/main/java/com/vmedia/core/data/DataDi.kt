package com.vmedia.core.data

import android.content.Context
import com.vmedia.core.data.datasource.CredentialsDataSource
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.datasource.SettingsDataSource
import com.vmedia.core.data.internal.NetworkCredentialsHolder
import com.vmedia.core.data.internal.database.UbilyDatabase
import com.vmedia.core.network.datasource.MutableNetworkCredentialsProvider
import com.vmedia.core.network.datasource.NetworkCredentialsProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.context.ModuleDefinition
import org.koin.dsl.definition.BeanDefinition
import org.koin.dsl.module.module

private const val PREF_CREDENTIALS = "PREF_CREDENTIALS"
private const val PREF_SETTINGS = "PREF_SETTINGS"

val dataModules by lazy {
    listOf(
        preferencesModule,
        dataBaseModule,
        networkModule
    )
}

private val preferencesModule = module {
    single { CredentialsDataSource(get(PREF_CREDENTIALS), get()) }
    single { SettingsDataSource(get(PREF_SETTINGS)) }
    single { NetworkCredentialsHolder() }

    single(PREF_CREDENTIALS) {
        androidApplication().getSharedPreferences(
            "248a648a38292ebc637233f00ff34546",
            Context.MODE_PRIVATE
        )
    }

    single(PREF_SETTINGS) {
        androidApplication().getSharedPreferences("ubily_settings", Context.MODE_PRIVATE)
    }
}

private val dataBaseModule = module {
    single { DatabaseDataSource(get()) }

    single { UbilyDatabase.getInstance(androidApplication(), "ubily_db") }

    dao { getAssetDao() }
    dao { getEventDao() }
    dao { getSaleDao() }
    dao { getPublisherDao() }
}

private val networkModule = module {
    single<NetworkCredentialsProvider> { get<MutableNetworkCredentialsProvider>() }
    single<MutableNetworkCredentialsProvider> { NetworkCredentialsHolder() }
}

private inline fun <reified T : Any> ModuleDefinition.dao(
    noinline definition: UbilyDatabase.() -> T
): BeanDefinition<T> {
    return single { definition.invoke(get()) }
}