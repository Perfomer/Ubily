package com.vmedia.ubily

import android.annotation.SuppressLint
import android.app.Application
import io.reactivex.rxkotlin.subscribeBy
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin

@Suppress("unused")
@SuppressLint("CheckResult")
class UbilyApplication : Application() {

    private val networkCredentialsSynchronizer: NetworkCredentialsSynchronizer by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin(
            androidContext = this,
            modules = koinModules
        )

        networkCredentialsSynchronizer.execute()
            .subscribeBy(Throwable::printStackTrace)
    }

}