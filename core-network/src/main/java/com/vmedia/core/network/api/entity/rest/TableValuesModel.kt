package com.vmedia.core.network.api.entity.rest

import com.google.gson.annotations.SerializedName

data class TableValuesModel(
    @SerializedName("aaData") val dataRow: List<List<String>>
)