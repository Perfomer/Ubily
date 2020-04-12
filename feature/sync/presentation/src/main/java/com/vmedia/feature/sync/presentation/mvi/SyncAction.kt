package com.vmedia.feature.sync.presentation.mvi

import com.vmedia.feature.sync.presentation.model.SyncStatus

internal sealed class SyncAction {

    class SyncStatusUpdated(val status: SyncStatus) : SyncAction()

}