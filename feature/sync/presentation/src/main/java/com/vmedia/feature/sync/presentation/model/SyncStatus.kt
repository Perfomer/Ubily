package com.vmedia.feature.sync.presentation.model

import android.os.Parcelable
import com.vmedia.feature.sync.presentation.model.SyncDataStatus.AWAITS
import kotlinx.android.parcel.Parcelize

@Parcelize
internal class SyncStatus(
    val dataStatuses: List<SyncDataItem> = emptyStatuses(),
    val isFinished: Boolean = false,
    val hasErrors: Boolean = false,
    val isAuthFailed: Boolean = false,
) : Parcelable

private fun emptyStatuses(): List<SyncDataItem> {
    return SyncDataType.values().map {
        SyncDataItem(
            type = it,
            status = AWAITS
        )
    }
}