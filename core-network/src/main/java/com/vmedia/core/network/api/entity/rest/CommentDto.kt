package com.vmedia.core.network.api.entity.rest

import java.util.*

data class CommentDto(
    val guid: String,
    val noteTitle: String,
    val assetShortUrl: String,
    val comment: String,
    val publishingDate: Date
)