package com.vmedia.core.data.internal.network.entity

import com.google.gson.annotations.SerializedName

data class ShortUrlModel(
    @SerializedName("short_url")
    val shortUrl: String
)