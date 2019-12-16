package com.vmedia.core.data.internal.network.entity.asset

import com.google.gson.annotations.SerializedName

data class LanguageMetadataModel(
    @SerializedName("key_images") val keyImages: AssetKeyImagesModel,
    @SerializedName("artwork") val artworks: List<ArtworkModel>,
    @SerializedName("release_notes") val releaseNotes: String = "",
    val keywords: String,
    val name: String,
    val description: String
)