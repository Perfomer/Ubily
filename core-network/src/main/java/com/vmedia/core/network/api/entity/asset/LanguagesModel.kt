package com.vmedia.core.network.api.entity.asset

import com.google.gson.annotations.SerializedName

internal data class LanguagesModel(
    @SerializedName("en-US") val englishUsa: LanguageMetadataModel
)