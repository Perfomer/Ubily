package com.vmedia.feature.sync.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal class SyncDataItem(
    val type: SyncDataType,
    val status: SyncDataStatus
) : Parcelable