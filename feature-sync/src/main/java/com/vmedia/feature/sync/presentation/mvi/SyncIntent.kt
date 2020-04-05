package com.vmedia.feature.sync.presentation.mvi

internal sealed class SyncIntent {

    object ObserveSyncStatus : SyncIntent()

    object StartSync : SyncIntent()

//    object CancelSync : SyncIntent()

}