package com.vmedia.core.network.entity

import androidx.annotation.IntRange
import java.util.*

internal data class CommentDto(
    val title: String,
    val authorName: String,
    @IntRange(from = 0, to = 5) val rating: Int,
    val comment: String,
    val publishingDate: Date,
    val assetShortUrl: String,
    val isPublisherReply: Boolean
)