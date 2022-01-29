package com.vmedia.feature.sync.presentation.mvi

internal sealed class SyncSubscription {

    object SyncFailed : SyncSubscription()

    object SyncFinished : SyncSubscription()

    object StartSyncService : SyncSubscription()
}