package com.vmedia.core.data

import android.content.Context
import com.vmedia.core.data.datasource.PreferencesDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val dataModule = module {
    single { PreferencesDataSource(get()) }

    single {
        androidApplication().getSharedPreferences(
            "248a648a38292ebc637233f00ff34546",
            Context.MODE_PRIVATE
        )
    }
}