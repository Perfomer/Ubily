package com.vmedia.feature.sync.presentation.mvi

internal sealed class SyncSubscription {

    object SyncFinished : SyncSubscription()

}