package com.vmedia.core.data

import android.content.Context
import com.vmedia.core.data.datasource.CredentialsDataSource
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.datasource.DatabaseDataSourceImpl
import com.vmedia.core.data.datasource.SettingsDataSource
import com.vmedia.core.data.internal.database.UbilyDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.context.ModuleDefinition
import org.koin.dsl.definition.BeanDefinition
import org.koin.dsl.module.module

val dataModules by lazy {
    listOf(
        preferencesModule,
        dataBaseModule
    )
}

private const val BEAN_PREF_CREDENTIALS = "PREF_CREDENTIALS"
private const val BEAN_PREF_SETTINGS = "PREF_SETTINGS"

private val preferencesModule = module {
    single { CredentialsDataSource(get(BEAN_PREF_CREDENTIALS), get()) }
    single { SettingsDataSource(get(BEAN_PREF_SETTINGS)) }

    single(BEAN_PREF_CREDENTIALS) {
        androidApplication().getSharedPreferences(
            "248a648a38292ebc637233f00ff34546",
            Context.MODE_PRIVATE
        )
    }

    single(BEAN_PREF_SETTINGS) {
        androidApplication().getSharedPreferences("ubily_settings", Context.MODE_PRIVATE)
    }
}

private val dataBaseModule = module {
    single<DatabaseDataSource> { DatabaseDataSourceImpl(get(), get()) }

    single { UbilyDatabase.getInstance(androidApplication(), "ubily_db") }

    dao { getAssetDao() }
    dao { getEventDao() }
    dao { getSaleDao() }
    dao { getPublisherDao() }
    dao { getReviewDao() }
    dao { getRevenueDao() }
    dao { getPayoutDao() }
}

private inline fun <reified T : Any> ModuleDefinition.dao(
    noinline definition: UbilyDatabase.() -> T
): BeanDefinition<T> {
    return single { definition.invoke(get()) }
}