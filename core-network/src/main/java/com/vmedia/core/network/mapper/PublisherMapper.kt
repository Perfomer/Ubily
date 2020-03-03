package com.vmedia.core.network.mapper

import com.vmedia.core.common.obj.creds.RssToken
import com.vmedia.core.common.util.Mapper
import com.vmedia.core.network.api.entity.publisher.PublisherDetailsModel
import com.vmedia.core.network.entity.PublisherDto
import com.vmedia.core.network.util.fixUrl

internal object PublisherMapper : Mapper<PublisherDetailsModel, PublisherDto> {

    override fun map(from: PublisherDetailsModel): PublisherDto {
        return PublisherDto(
            organizationId = from.organizationId,
            name = from.name,
            description = from.description,
            url = from.shortUrl,
            smallImageUrl = from.keyImages.small.fixUrl(),
            largeImageUrl = from.keyImages.big.fixUrl(),
            rssToken = from.activityUrl.extractToken()
        )
    }

    private fun String.extractToken(): RssToken {
        val startAnchor = "feed/"

        val startIndex: Int = indexOf(startAnchor) + startAnchor.length
        val endIndex: Int = indexOf("/", startIndex)

        return RssToken(
            publisherName = substring(startIndex, endIndex),
            token = substring(endIndex + 1, indexOf("/activity"))
        )
    }

}