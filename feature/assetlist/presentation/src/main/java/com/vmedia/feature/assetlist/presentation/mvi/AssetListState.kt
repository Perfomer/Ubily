package com.vmedia.feature.assetlist.presentation.mvi

import android.os.Parcelable
import com.vmedia.feature.assetlist.domain.model.AssetShortInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class AssetListState(
    val isLoading: Boolean = false,
    val payload: List<AssetShortInfo> = emptyList(),
    val publisherAvatar: String = "",
    val error: Throwable? = null
) : Parcelable