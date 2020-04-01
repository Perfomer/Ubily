package com.vmedia.core.network.api.entity

import com.google.gson.annotations.SerializedName

internal data class TableValuesModel(
    @SerializedName("aaData") val dataRows: List<List<String>>,
    @SerializedName("result") val extraRows: List<ExtraTableValues>?
)

internal data class ExtraTableValues(
    @SerializedName("short_url") val shortUrl: String,
    @SerializedName("net") val net: String
)