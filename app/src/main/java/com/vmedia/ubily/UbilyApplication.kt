package com.vmedia.ubily

import android.annotation.SuppressLint
import android.app.Application
import com.facebook.stetho.Stetho
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class UbilyApplication : Application() {

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this);

        startKoin {
            if (BuildConfig.DEBUG) androidLogger()
            modules(koinModules)
            androidContext(this@UbilyApplication)
        }

        get<NetworkCredentialsSynchronizer>().execute()
            .subscribeOn(Schedulers.io())
            .subscribeBy(Throwable::printStackTrace)
    }

}