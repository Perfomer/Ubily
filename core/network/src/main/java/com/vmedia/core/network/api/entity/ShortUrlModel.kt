package com.vmedia.core.network.api.entity

import com.google.gson.annotations.SerializedName

internal data class ShortUrlModel(
    @SerializedName("short_url")
    val shortUrl: String
)