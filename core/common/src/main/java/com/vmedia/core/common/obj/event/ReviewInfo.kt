package com.vmedia.core.common.obj.event

import android.os.Parcelable
import com.vmedia.core.common.obj.HollowRating
import com.vmedia.core.common.obj.Rating
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewInfo(
    @Rating val rating: Int = 1,
    val authorName: String = "",
    val reviewTitle: String = "",
    val reviewBody: String = "",
    val publisherReplyBody: String? = null,
    val assetId: Long,
    val assetIcon: String? = null,
    val assetName: String = "",
    @HollowRating val assetAverageRating: Int = 5
) : Parcelable