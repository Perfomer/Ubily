package com.vmedia.core.network.api.entity.publisher

import com.google.gson.annotations.SerializedName

internal data class PublisherResponseModel(
    @SerializedName("result") val result: PublisherWrapModel
)