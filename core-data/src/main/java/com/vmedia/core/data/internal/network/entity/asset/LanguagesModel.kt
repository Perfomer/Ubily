package com.vmedia.core.data.internal.network.entity.asset

import com.google.gson.annotations.SerializedName

data class LanguagesModel(
    @field:SerializedName("en-US")
    val englishUsa: LanguageMetadataModel
)