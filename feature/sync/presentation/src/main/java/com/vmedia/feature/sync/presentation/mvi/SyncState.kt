package com.vmedia.feature.sync.presentation.mvi

import android.os.Parcelable
import com.vmedia.feature.sync.presentation.model.SyncStatus
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class SyncState(
    val inProgress: Boolean = false,
    val status: SyncStatus = SyncStatus()
) : Parcelable