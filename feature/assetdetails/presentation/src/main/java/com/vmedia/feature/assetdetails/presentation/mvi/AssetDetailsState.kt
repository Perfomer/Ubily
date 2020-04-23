package com.vmedia.feature.assetdetails.presentation.mvi

import android.os.Parcelable
import com.vmedia.feature.assetdetails.domain.model.AssetDetails
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class AssetDetailsState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val payload: AssetDetails = AssetDetails()
): Parcelable
