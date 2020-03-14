package com.vmedia.core.network.mapper

import com.vmedia.core.common.util.FORMAT_RSS
import com.vmedia.core.common.util.Mapper
import com.vmedia.core.common.util.parse
import com.vmedia.core.network.api.entity.rss.RssItemModel
import com.vmedia.core.network.entity.ReviewDto

internal object ReviewMapper : Mapper<RssItemModel, ReviewDto> {

    override fun map(from: RssItemModel): ReviewDto {
        val commentBody = from.description
        val commentTitle = commentBody.extractTitle()
        val isPublisherReply = commentTitle == "Reply from publisher"

        return ReviewDto(
            title = if (isPublisherReply) "" else commentTitle,
            comment = commentBody.extractComment(),
            rating = if (isPublisherReply) 0 else commentBody.extractRating(),
            isPublisherReply = isPublisherReply,
            authorName = from.title.extractAuthorName(),
            assetShortUrl = from.link,
            publishingDate = from.publishingDate.parse(FORMAT_RSS)
        )
    }

    private fun String.extractTitle() : String {
        return drop(4).substringBefore("</h1>")
    }

    private fun String.extractComment() : String {
        return substringAfter("<p>").substringBefore("</p>")
    }

    private fun String.extractRating(): Int {
        return dropLast(4).substringAfterLast("user: ").length / 7
    }

    private fun String.extractAuthorName() : String {
        return substringAfterLast("by ")
    }

}