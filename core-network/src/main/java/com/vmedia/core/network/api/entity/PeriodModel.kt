package com.vmedia.core.network.api.entity

import com.google.gson.annotations.SerializedName

internal data class PeriodModel(
    @SerializedName("value")
    val value: String,
    @SerializedName("name")
    val name: String
)