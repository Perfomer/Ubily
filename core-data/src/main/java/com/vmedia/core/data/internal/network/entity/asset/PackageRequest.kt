package com.vmedia.core.data.internal.network.entity.asset

import com.google.gson.annotations.SerializedName

data class PackageRequest(
    val packages: List<PackageModel>,

    @SerializedName("publisher_name")
    val publisherName: String,

    @SerializedName("terms_current")
    val termsCurrent: String,

    @SerializedName("terms_accepted")
    val termsAccepted: String,

    @SerializedName("publisher_id")
    val publisherId: String
)