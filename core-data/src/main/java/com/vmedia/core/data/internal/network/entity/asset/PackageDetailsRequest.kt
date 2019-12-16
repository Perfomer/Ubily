package com.vmedia.core.data.internal.network.entity.asset

import com.google.gson.annotations.SerializedName

data class PackageDetailsRequest(
    @SerializedName("allow_submit")
    val allowSubmit: Boolean,

    @SerializedName("publisher_name")
    val publisherName: String,

    @SerializedName("publisher_id")
    val publisherId: String,

    @SerializedName("terms_current")
    val termsCurrent: String,

    @SerializedName("terms_accepted")
    val termsAccepted: String,

    @SerializedName("package")
    val packageModel: PackageModel
)