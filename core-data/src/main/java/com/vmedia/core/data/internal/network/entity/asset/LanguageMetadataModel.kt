package com.vmedia.core.data.internal.network.entity.asset

import com.google.gson.annotations.SerializedName
import com.vmedia.core.data.internal.network.entity.asset.ArtworkModel
import com.vmedia.core.data.internal.network.entity.asset.KeyImagesModel

data class LanguageMetadataModel(
    @SerializedName("key_images")
    val keyImages: KeyImagesModel,

    val keywords: String,

    @field:SerializedName("artwork")
    val artworks: List<ArtworkModel>,

    val name: String,

    val description: String,

    @field:SerializedName("release_notes")
    val releaseNotes: String = ""
)