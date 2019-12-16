package com.vmedia.core.data.internal.network.entity.asset

import com.google.gson.annotations.SerializedName

data class PackageModel(
    val versions: List<PackageVersionModel>,

    val version: PackageVersionModel,

    @SerializedName("category_id")
    val categoryId: String,

    @SerializedName("management_flags")
    val managementFlags: String,

    val name: String,

    val id: String,

    @SerializedName("short_url")
    val shortUrl: String
)