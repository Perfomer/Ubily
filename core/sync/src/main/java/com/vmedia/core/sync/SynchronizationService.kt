package com.vmedia.core.sync

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.vmedia.core.common.pure.util.rx.safeDispose
import com.vmedia.core.sync.datasource.SynchronizationDataSource
import com.vmedia.core.sync.notification.SyncStatusNotificationManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import timber.log.Timber

class SynchronizationService : Service() {

    private val synchronizationDataSource: SynchronizationDataSource by inject()
    private val notificationManager: SyncStatusNotificationManager by inject()

    private var synchronizationDisposable: Disposable? = null
    private var statusDisposable: Disposable? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startForeground(
            SyncStatusNotificationManager.NOTIFICATION_ID,
            notificationManager.createInitialNotification()
        )

        synchronizationDisposable = synchronizationDataSource.synchronize()
            .doOnSubscribe { startNotificationManager() }
            .doOnTerminate { stopNotificationManager() }
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onComplete = {
                    stopForeground(false)
                },
                onError = { error ->
                    Timber.e(error)
                    stopForeground(true)
                }
            )

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()

        synchronizationDisposable.safeDispose()
        statusDisposable.safeDispose()
    }

    private fun startNotificationManager() {
        statusDisposable = synchronizationDataSource.getSynchronizationStatus()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = Timber::e,
                onNext = notificationManager::onStatusUpdated
            )
    }

    private fun stopNotificationManager() {
        statusDisposable?.dispose()
        notificationManager.onDisposed()
    }

    companion object {

        fun startForeground(context: Context) {
            val intent = Intent(context, SynchronizationService::class.java)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
    }
}