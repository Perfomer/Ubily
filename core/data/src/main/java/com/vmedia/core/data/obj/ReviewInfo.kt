package com.vmedia.core.data.obj

import android.os.Parcelable
import androidx.annotation.IntRange
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewInfo(
    @IntRange(from = 1, to = 5) val rating: Int = 1,
    val authorName: String = "",
    val reviewTitle: String = "",
    val reviewBody: String = "",
    val publisherReplyBody: String? = null,
    val assetId: Long,
    val assetIcon: String? = null,
    val assetName: String = "",
    @IntRange(from = 0, to = 5) val assetAverageRating: Int = 5
) : Parcelable