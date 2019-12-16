package com.vmedia.core.data.internal.network.entity.publisher

import com.google.gson.annotations.SerializedName

data class PublisherWrapModel(
    @SerializedName("success") val success: Boolean,
    @SerializedName("terms_current") val termsCurrent: Int,
    @SerializedName("publisher") val publisher: PublisherDetailsModel,
    @SerializedName("terms_accepted") val termsAccepted: Int,
    @SerializedName("message") val message: String
)