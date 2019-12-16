package com.vmedia.core.data.internal.network.entity.asset

import com.google.gson.annotations.SerializedName

data class PackageModelWithVersions(
    @SerializedName("management_flags") val managementFlags: String,
    @SerializedName("category_id") val categoryId: Long,
    @SerializedName("short_url") val shortUrl: String,
    @SerializedName("average_rating") val averageRating: Int,
    @SerializedName("count_ratings") val countRatings: Int,
    val id: Long,
    val name: String,
    val versions: List<PackageVersionModel>
)