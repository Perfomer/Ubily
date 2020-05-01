package com.vmedia.core.network.filter

import com.vmedia.core.common.android.util.Filter
import com.vmedia.core.network.entity.internal.ReviewDto

internal object ReviewFilter : Filter<ReviewDto> {

    override fun filter(source: List<ReviewDto>): List<ReviewDto> {
        val result = mutableListOf<ReviewDto>()

        for (review: ReviewDto in source) {
            val duplicateIndex = result.indexOfFirst { it.isDuplicateOf(review) }
            val duplicate by lazy { result[duplicateIndex] }

            if (duplicateIndex == -1) {
                result += review
            } else {
                result -= duplicate
                result += selectNewer(review, duplicate)
            }
        }

        return result
    }

    private fun selectNewer(vararg reviews: ReviewDto): ReviewDto {
        return reviews.maxBy(ReviewDto::publishingDate)!!
    }

    private fun ReviewDto.isDuplicateOf(other: ReviewDto): Boolean {
        return this.assetShortUrl == other.assetShortUrl &&
                this.authorName == other.authorName &&
                this.isPublisherReply == other.isPublisherReply
    }

}