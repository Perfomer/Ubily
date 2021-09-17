package com.vmedia.core.sync.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.vmedia.core.common.android.util.notificationManager
import com.vmedia.core.sync.R
import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync.SynchronizationDataType.*
import com.vmedia.core.sync.SynchronizationEvent
import com.vmedia.core.sync.SynchronizationStatus
import java.util.concurrent.TimeUnit

internal class SyncStatusNotificationManager(
    private val context: Context,
) {

    private val notificationManager: NotificationManagerCompat = context.notificationManager

    internal fun onStatusUpdated(status: SynchronizationStatus) {
        createChannel()

        if (status.isFinished) {
            removeNotification()
        } else {
            val notification = createNotification(
                description = getNowReceivingItem(status) ?: return,
                maxProgress = status.maxEventsCount,
                currentProgress = status.readyEventsCount
            )

            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    internal fun onDisposed() = removeNotification()

    private fun createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.status_channel_title),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.getString(R.string.status_channel_description)
        }

        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(
        description: String,
        maxProgress: Int,
        currentProgress: Int,
    ): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_ubily)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setTimeoutAfter(TimeUnit.MINUTES.toMillis(3))
            .setContentTitle(context.getString(R.string.status_notification_title))
            .setProgress(maxProgress, currentProgress, false)
            .setContentText(description)
            .build()
    }

    private fun removeNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
    }

    private fun getNowReceivingItem(status: SynchronizationStatus): String? {
        val receivingItem: SynchronizationDataType? = status.events.entries
            .findLast { it.value is SynchronizationEvent.Loading }?.key

        return receivingItem?.nameResource?.let(context::getString)
    }

    private companion object {

        private const val CHANNEL_ID: String = "SyncStatus"
        private const val NOTIFICATION_ID: Int = 201

        @get:StringRes
        private val SynchronizationDataType.nameResource: Int
            get() = when (this) {
                PERIODS, PUBLISHER -> R.string.status_notification_description_publisher
                ASSETS_CATEGORIES, ASSETS -> R.string.status_notification_description_assets
                USERS, REVIEWS -> R.string.status_notification_description_reviews
                SALES -> R.string.status_notification_description_sales
                FREE_DOWNLOADS -> R.string.status_notification_description_free_downloads
                REVENUES, PAYOUTS -> R.string.status_notification_description_revenue
            }
    }
}
