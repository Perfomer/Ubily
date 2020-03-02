package com.vmedia.core.network.api.entity.rest

import com.google.gson.annotations.SerializedName

data class PeriodModel(
    @SerializedName("value")
    val value: String,
    @SerializedName("name")
    val name: String
)