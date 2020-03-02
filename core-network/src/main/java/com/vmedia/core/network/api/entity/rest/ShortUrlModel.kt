package com.vmedia.core.network.api.entity.rest

import com.google.gson.annotations.SerializedName

data class ShortUrlModel(
    @SerializedName("short_url")
    val shortUrl: String
)