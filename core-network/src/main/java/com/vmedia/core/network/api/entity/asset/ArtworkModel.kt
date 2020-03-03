package com.vmedia.core.network.api.entity.asset

internal data class ArtworkModel(
    val id: Long,
    val uri: String = "",
    val type: String,
    val reference: String,
    val scaled: String
)