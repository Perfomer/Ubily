package com.vmedia.ubily

import android.annotation.SuppressLint
import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class UbilyApplication : Application() {

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this);

        Timber.plant(Timber.DebugTree())

        startKoin {
            if (BuildConfig.DEBUG) androidLogger()
            modules(koinModules)
            androidContext(this@UbilyApplication)
        }
    }

}