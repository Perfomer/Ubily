package com.vmedia.core.network.entity

import com.vmedia.core.common.android.obj.creds.RssToken

data class PublisherDto(
    val organizationId: Long,
    val name: String,
    val description: String,
    val url: String,
    val smallImageUrl: String?,
    val largeImageUrl: String?,
    val rssToken: RssToken
)