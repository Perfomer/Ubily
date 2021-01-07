package com.vmedia.core.sync

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.vmedia.core.sync.datasource.SynchronizationDataSource
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * @author d.balchugov
 */
class SyncWorker(
    private val context: Context,
    workerParams: WorkerParameters,
) : RxWorker(context, workerParams), KoinComponent {

    private val synchronizationDataSource: SynchronizationDataSource by inject()

    private val notificationManager: NotificationManager? = context.notificationManager

    override fun createWork(): Single<Result> {
        return synchronizationDataSource.synchronize()
            .doOnSubscribe { makeStatusNotification("TEST") }
            .doAfterTerminate { notificationManager?.cancel(NOTIFICATION_ID) }
            .andThen(Single.just(Result.success()))
    }

    private fun makeStatusNotification(message: String) {
        createChannel()

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_ubily)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        context.notifyCompat(NOTIFICATION_ID, builder.build())
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            CHANNEL_ID,
            VERBOSE_NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        }

        notificationManager?.createNotificationChannel(channel)
    }

    companion object {

        private val Context.notificationManager: NotificationManager?
            get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        private const val VERBOSE_NOTIFICATION_CHANNEL_NAME: String = "Verbose WorkManager Notifications"
        private const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION: String = "Shows notifications whenever work starts"
        private const val NOTIFICATION_TITLE: String = "WorkRequest Starting"
        private const val CHANNEL_ID: String = "VERBOSE_NOTIFICATION"
        private const val NOTIFICATION_ID: Int = 201

        private const val WORKER_NAME = "SyncWorker"

        fun startOnceImmediately(workManager: WorkManager) {
            workManager.enqueueUniqueWork(
                WORKER_NAME,
                ExistingWorkPolicy.KEEP,
                OneTimeWorkRequest.from(SyncWorker::class.java),
            )
        }

        private fun Context.notifyCompat(id: Int, notification: Notification) {
            NotificationManagerCompat.from(this).notify(id, notification)
        }
    }
}
