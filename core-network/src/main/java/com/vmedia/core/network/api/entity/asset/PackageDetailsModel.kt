package com.vmedia.core.network.api.entity.asset

import com.google.gson.annotations.SerializedName

internal data class PackageDetailsModel(
    @SerializedName("allow_submit") val allowSubmit: Boolean,
    @SerializedName("allow_publish") val allowPublish: Boolean,
    @SerializedName("terms_current") val termsCurrent: Int,
    @SerializedName("terms_accepted") val termsAccepted: Int,
    @SerializedName("package") val packageModel: PackageModel,
    @SerializedName("publisher_name") val publisherName: String,
    @SerializedName("publisher_id") val publisherId: String,
    val draft: String
)