package com.vmedia.core.common.pure.obj.event

import android.os.Parcelable
import com.vmedia.core.common.pure.obj.AssetStatus
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AssetInfo(
    val id: Long,
    val icon: String?,
    val name: String,
    val version: String,
    val status: AssetStatus
) : Parcelable