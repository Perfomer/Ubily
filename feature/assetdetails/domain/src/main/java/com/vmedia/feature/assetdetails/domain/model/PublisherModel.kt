package com.vmedia.feature.assetdetails.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PublisherModel(
    val id: Long = 0,
    val avatar: String = "",
    val name: String = "",
    val description: String = "",
    val averageRating: Double = 0.0
) : Parcelable