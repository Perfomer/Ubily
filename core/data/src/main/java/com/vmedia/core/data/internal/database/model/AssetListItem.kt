package com.vmedia.core.data.internal.database.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AssetListItem(
    val id: Long,
    val name: String,
    val categoryName: String,
    val largeImage: String,
    val iconImage: String,
    val reviewsCount: Int,
    val averageRating: Int
): Parcelable