package com.vmedia.core.network.api.entity.rest.asset

import com.google.gson.annotations.SerializedName

data class PackageVersionFullModel(
    @SerializedName("package_id") val packageId: Long,
    @SerializedName("version_name") val versionName: String,
    @SerializedName("category_id") val categoryId: Long,
    @SerializedName("package_version_id") val packageVersionId: Long,
    @SerializedName("publishnotes") val publishNotes: String,
    @SerializedName("languages") val languagesModel: LanguagesModel,
    @SerializedName("unitypackages") val unityPackages: List<PackageTechInfoModel>,
    @SerializedName("packmanpackages") val packmanPackages : List<String>,
    val name: String,
    val id: Long,
    val size: Long,
    val modified: String,
    val created: String,
    val published: String,
    val status: String,
    val price: String
)