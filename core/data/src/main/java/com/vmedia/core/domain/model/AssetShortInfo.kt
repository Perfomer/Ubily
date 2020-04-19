package com.vmedia.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AssetShortInfo(
    val id: Long,
    val name: String,
    val categoryName: String,
    val largeImage: String,
    val iconImage: String,
    val reviewsCount: Int,
    val averageRating: Int
) : Parcelable