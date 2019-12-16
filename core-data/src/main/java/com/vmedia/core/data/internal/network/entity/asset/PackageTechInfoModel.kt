package com.vmedia.core.data.internal.network.entity.asset

import com.google.gson.annotations.SerializedName

data class PackageTechInfoModel(
    @SerializedName("package_upload_id") val packageUploadId: Long,
    @SerializedName("unity_version") val unity_version: String,
    @SerializedName("asset_store_tools_version") val assetStoreToolsVersion: String,
    val timestamp: String,
    val path: String,
    val size: Long
)