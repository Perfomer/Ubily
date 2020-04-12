package com.vmedia.core.network.entity

import java.util.*

data class DownloadDto(
    val assetName: String,
    val assetUrl: String,
    val downloadsQuantity: Int,
    val firstDownload: Date,
    val lastDownload: Date
)