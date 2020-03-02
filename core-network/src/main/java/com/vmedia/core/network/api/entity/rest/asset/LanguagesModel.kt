package com.vmedia.core.network.api.entity.rest.asset

import com.google.gson.annotations.SerializedName

data class LanguagesModel(
    @SerializedName("en-US") val englishUsa: LanguageMetadataModel
)