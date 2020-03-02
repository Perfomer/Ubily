package com.vmedia.core.network.mapper

import com.vmedia.core.common.util.FORMAT_RSS
import com.vmedia.core.common.util.Mapper
import com.vmedia.core.common.util.parse
import com.vmedia.core.network.api.entity.CommentDto
import com.vmedia.core.network.api.entity.rest.rss.RssItemModel

object CommentMapper : Mapper<RssItemModel, CommentDto> {

    override fun map(from: RssItemModel): CommentDto {
        return CommentDto(
            guid = from.guid,
            noteTitle = from.title,
            assetShortUrl = from.link,
            comment = from.description,
            publishingDate = from.publishingDate.parse(FORMAT_RSS)
        )
    }

}