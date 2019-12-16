package com.vmedia.core.data.internal.network.entity

import com.google.gson.annotations.SerializedName

data class TableValuesModel(
    @SerializedName("aaData")
    val dataRow: List<List<String>>,
    @SerializedName("result")
    val shortUrl: List<String>
)