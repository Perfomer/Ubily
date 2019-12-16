package com.vmedia.core.data.internal.network.entity.asset

import com.google.gson.annotations.SerializedName

data class AssetKeyImagesModel(
    @SerializedName("big_v2") val bigUpdated : String = "",
    @SerializedName("small_v2") val smallUpdated : String = "",
    val small: String = "",
    val big: String = "",
    val icon: String = "",
    val facebook: String = ""
)