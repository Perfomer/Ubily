package com.vmedia.core.data.obj

import android.os.Parcelable
import androidx.annotation.IntRange
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewInfo(
    @IntRange(from = 1, to = 5) val rating: Int,
    val authorName: String,
    val reviewTitle: String,
    val reviewBody: String,
    val assetId: Long,
    val assetIcon: String,
    val assetName: String,
    val assetAverageRating: Double
): Parcelable