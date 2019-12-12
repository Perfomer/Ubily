package com.vmedia.ubily

import android.app.Application
import org.koin.android.ext.android.startKoin

@Suppress("unused")
class UbilyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(
            androidContext = this,
            modules = koinModules
        )
    }

}