package com.vmedia.core.network.entity

import androidx.annotation.IntRange
import java.util.*

data class DetailedCommentDto(
    val title: String,
    val authorName: String,
    @IntRange(from = 1, to = 5) val rating: Int,
    val comment: String,
    val publishingDate: Date,
    val assetShortUrl: String,
    val publisherReply: String?,
    val publisherReplyDate: Date?
)