package com.vmedia.core.network.api.entity

import com.google.gson.annotations.SerializedName

internal data class TableValuesModel(
    @SerializedName("aaData") val dataRow: List<List<String>>
)