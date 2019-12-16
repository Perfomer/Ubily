package com.vmedia.core.data.internal.network.entity.publisher

import com.google.gson.annotations.SerializedName

data class PublisherResponseModel(
    @SerializedName("result") val result: PublisherWrapModel
)