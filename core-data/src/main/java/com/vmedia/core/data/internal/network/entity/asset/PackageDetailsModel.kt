package com.vmedia.core.data.internal.network.entity.asset

import com.google.gson.annotations.SerializedName

data class PackageDetailsModel(
    @SerializedName("allow_submit") val allowSubmit: Boolean,
    @SerializedName("allow_publish") val allowPublish: Boolean,
    @SerializedName("terms_current") val termsCurrent: Int,
    @SerializedName("terms_accepted") val termsAccepted: Int,
    @SerializedName("package") val packageModel: PackageModel,
    @SerializedName("publisher_name") val publisherName: String,
    @SerializedName("publisher_id") val publisherId: String,
    val draft: String
)