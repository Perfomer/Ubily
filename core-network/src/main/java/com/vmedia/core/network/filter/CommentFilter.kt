package com.vmedia.core.network.filter

import com.vmedia.core.common.util.Filter
import com.vmedia.core.network.api.entity.CommentDto

internal object CommentFilter : Filter<CommentDto> {

    override fun filter(source: List<CommentDto>): List<CommentDto> {
        val result = mutableListOf<CommentDto>()

        for (comment: CommentDto in source) {
            val duplicateIndex = result.indexOfFirst { it.isDuplicateOf(comment) }
            val duplicate by lazy { result[duplicateIndex] }

            if (duplicateIndex == -1) {
                result += comment
            } else {
                result -= duplicate
                result += selectNewer(comment, duplicate)
            }
        }

        return result
    }

    private fun selectNewer(vararg comments: CommentDto): CommentDto {
        return comments.maxBy(CommentDto::publishingDate)!!
    }

    private fun CommentDto.isDuplicateOf(other: CommentDto): Boolean {
        return this.assetShortUrl == other.assetShortUrl &&
                this.authorName == other.authorName &&
                this.isPublisherReply == other.isPublisherReply
    }

}