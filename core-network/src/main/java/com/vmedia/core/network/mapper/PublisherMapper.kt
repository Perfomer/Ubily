package com.vmedia.core.network.mapper

import com.vmedia.core.common.util.Mapper
import com.vmedia.core.network.api.entity.PublisherDto
import com.vmedia.core.network.api.entity.rest.publisher.PublisherDetailsModel
import com.vmedia.core.network.obj.RssToken
import com.vmedia.core.network.util.fixUrl

object PublisherMapper : Mapper<PublisherDetailsModel, PublisherDto> {

    override fun map(from: PublisherDetailsModel): PublisherDto {
        return PublisherDto(
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