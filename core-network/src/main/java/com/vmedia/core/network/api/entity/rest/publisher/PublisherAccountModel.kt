package com.vmedia.core.network.api.entity.rest.publisher

import com.google.gson.annotations.SerializedName

internal data class PublisherAccountModel(
    @SerializedName("id") val id: Long,
    @SerializedName("email") val email: String,
    @SerializedName("label") val label: String
)