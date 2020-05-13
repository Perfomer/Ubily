package com.vmedia.core.common.pure.obj.event

import com.vmedia.core.common.pure.obj.HollowRating
import com.vmedia.core.common.pure.obj.Rating

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
)