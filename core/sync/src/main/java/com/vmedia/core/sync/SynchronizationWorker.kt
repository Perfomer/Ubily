package com.vmedia.core.sync

import android.content.Context
import androidx.work.*
import com.vmedia.core.sync.datasource.SynchronizationDataSource
import com.vmedia.core.sync.notification.SyncStatusNotificationManager
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class SynchronizationWorker(
    context: Context,
    workerParams: WorkerParameters,
) : RxWorker(context, workerParams), KoinComponent {

    private val synchronizationDataSource: SynchronizationDataSource by inject()
    private val notificationManager: SyncStatusNotificationManager by inject()

    private var statusDisposable: Disposable? = null

    override fun createWork(): Single<Result> {
        return synchronizationDataSource.synchronize()
            .doOnSubscribe { startNotificationManager() }
            .doOnTerminate { stopNotificationManager() }
            .andThen(Single.just(Result.success()))
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

        private const val WORKER_NAME = "SyncWorker"

        fun startOnceImmediately(workManager: WorkManager) {
            workManager.enqueueUniqueWork(
                WORKER_NAME,
                ExistingWorkPolicy.KEEP,
                OneTimeWorkRequest.from(SynchronizationWorker::class.java),
            )
        }
    }
}
