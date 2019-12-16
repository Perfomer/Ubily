package com.vmedia.core.data.internal.network.entity.asset

import com.google.gson.annotations.SerializedName
import com.vmedia.core.data.internal.network.entity.asset.LanguagesModel
import com.vmedia.core.data.internal.network.entity.asset.PackageTechInfo

data class PackageVersionModel(
    val status: String,

    @SerializedName("unitypackages")
    val packageTechInfo: PackageTechInfo,

    val name: String,

    @SerializedName("package_id")
    val packageId: String,

    val size: Long,

    val modified: String,

    val created: String,

    val published: String,

    @SerializedName("version_name")
    val versionName: String,

    @SerializedName("category_id")
    val categoryId: String,

    val languages: LanguagesModel,

    @SerializedName("package_version_id")
    val packageVersionId: String,

    @SerializedName("publishnotes")
    val publishNotes: String,

    val id: String,

    val price: String
)