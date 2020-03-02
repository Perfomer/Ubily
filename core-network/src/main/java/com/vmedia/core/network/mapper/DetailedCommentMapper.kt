package com.vmedia.core.network.mapper

import com.vmedia.core.common.util.Mapper
import com.vmedia.core.network.api.entity.CommentDto
import com.vmedia.core.network.api.entity.DetailedCommentDto
import java.util.*

internal object DetailedCommentMapper : Mapper<List<CommentDto>, List<DetailedCommentDto>> {

    override fun map(from: List<CommentDto>): List<DetailedCommentDto> {
        val result = mutableListOf<DetailedCommentDto>()
        val notAnsweredComments = mutableListOf<CommentDto>()

        for (comment: CommentDto in from) {
            val duplicateIndex = notAnsweredComments.indexOfFirst { it.isDuplicateOf(comment) }
            val duplicate by lazy { notAnsweredComments[duplicateIndex] }

            if (duplicateIndex == -1) {
                notAnsweredComments += comment
            } else {
                notAnsweredComments -= duplicate
                result += zipComments(comment, duplicate)
            }
        }

        result += notAnsweredComments.map { it.toDetailedCommentDto() }

        return result
    }

    private fun zipComments(comment1: CommentDto, comment2: CommentDto): DetailedCommentDto {
        val publisherReply = if (comment1.isPublisherReply) comment1 else comment2
        val comment = if (comment2.isPublisherReply) comment1 else comment2

        return comment.toDetailedCommentDto(publisherReply.comment, publisherReply.publishingDate)
    }

    private fun CommentDto.isDuplicateOf(other: CommentDto): Boolean {
        return this.assetShortUrl == other.assetShortUrl &&
                this.authorName == other.authorName
    }

    private fun CommentDto.toDetailedCommentDto(
        publisherReply: String? = null,
        publisherReplyDate: Date? = null
    ): DetailedCommentDto {
        return DetailedCommentDto(
            title = title,
            authorName = authorName,
            rating = rating,
            comment = comment,
            publishingDate = publishingDate,
            assetShortUrl = assetShortUrl,
            publisherReply = publisherReply,
            publisherReplyDate = publisherReplyDate
        )
    }

}