package com.vmedia.core.network.api.entity

import com.vmedia.core.network.obj.RssToken

data class PublisherDto(
    val organizationId: Long,
    val name: String,
    val description: String,
    val url: String,
    val smallImageUrl: String?,
    val largeImageUrl: String?,
    val rssToken: RssToken
)