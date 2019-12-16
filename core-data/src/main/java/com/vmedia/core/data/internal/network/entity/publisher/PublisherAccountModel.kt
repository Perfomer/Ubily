package com.vmedia.core.data.internal.network.entity.publisher

import com.google.gson.annotations.SerializedName

data class PublisherAccountModel(
    @SerializedName("id") val id: Long,
    @SerializedName("email") val email: String,
    @SerializedName("label") val label: String
)