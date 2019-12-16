package com.vmedia.core.data.internal.network.entity.asset

import com.google.gson.annotations.SerializedName

data class PackageTechInfo(
    @SerializedName("package_upload_id")
    val packageUploadId: String,

    @SerializedName("unity_version")
    val unity_version: String,

    val timestamp: String,

    @SerializedName("asset_store_tools_version")
    val assetStoreToolsVersion: String,

    val path: String,

    val size: String
)