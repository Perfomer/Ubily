package com.vmedia.core.network.entity

import com.vmedia.core.common.pure.obj.Rating
import java.util.*

data class DetailedReviewDto(
    val title: String,
    val authorName: String,
    @Rating val rating: Int,
    val comment: String,
    val publishingDate: Date,
    val assetShortUrl: String,
    val publisherReply: String?,
    val publisherReplyDate: Date?
)