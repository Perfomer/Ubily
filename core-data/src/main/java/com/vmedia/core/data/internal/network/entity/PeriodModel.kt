package com.vmedia.core.data.internal.network.entity

import com.google.gson.annotations.SerializedName

data class PeriodModel(
    @SerializedName("value")
    val value: String,
    @SerializedName("name")
    val name: String
)