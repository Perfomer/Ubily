package com.vmedia.core.network.api.entity.rest.publisher

import com.google.gson.annotations.SerializedName

data class PublisherResponseModel(
    @SerializedName("result") val result: PublisherWrapModel
)