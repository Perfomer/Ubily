package com.vmedia.core.network.entity

data class AssetDetailsDto(
    val description: String,
    val smallImageUrl: String?,
    val iconImageUrl: String?,
    val bigImageUrl: String?,
    val facebookImageUrl: String?,
    val tags: Set<String>,
    val artworksUrls: Set<String>
)