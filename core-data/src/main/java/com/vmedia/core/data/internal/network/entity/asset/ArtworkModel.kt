package com.vmedia.core.data.internal.network.entity.asset

data class ArtworkModel(
    val id: Long,
    val uri: String,
    val type: String,
    val reference: String,
    val scaled: String
)