package com.vmedia.core.network.entity.internal

import com.vmedia.core.common.pure.obj.HollowRating
import java.util.*

internal data class ReviewDto(
    val title: String,
    val authorName: String,
    @HollowRating val rating: Int,
    val comment: String,
    val publishingDate: Date,
    val assetShortUrl: String,
    val isPublisherReply: Boolean
)