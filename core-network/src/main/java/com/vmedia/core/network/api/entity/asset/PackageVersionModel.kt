package com.vmedia.core.network.api.entity.asset

import com.google.gson.annotations.SerializedName

internal data class PackageVersionModel(
    @SerializedName("package_id") val packageId: Long,
    @SerializedName("version_name") val versionName: String,
    @SerializedName("category_id") val categoryId: Long,
    @SerializedName("package_version_id") val packageVersionId: Long,
    @SerializedName("publishnotes") val publishNotes: String,
    val name: String,
    val id: String,
    val size: Long?,
    val modified: String,
    val created: String,
    val published: String?,
    val status: String,
    val price: String
)