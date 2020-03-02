package com.vmedia.core.network.api.entity

import java.util.*

data class DownloadDto(
    val assetName: String,
    val downloadsQuantity: Int,
    val firstDownload: Date,
    val lastDownload: Date
)