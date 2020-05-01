package com.vmedia.core.network.mapper

import android.annotation.SuppressLint
import com.vmedia.core.common.android.util.Mapper
import com.vmedia.core.network.entity.DetailedReviewDto
import com.vmedia.core.network.entity.internal.ReviewDto
import java.util.*

internal object DetailedReviewMapper : Mapper<List<ReviewDto>, List<DetailedReviewDto>> {

    override fun map(from: List<ReviewDto>): List<DetailedReviewDto> {
        val result = mutableListOf<DetailedReviewDto>()
        val notAnsweredComments = mutableListOf<ReviewDto>()

        for (review: ReviewDto in from) {
            val duplicateIndex = notAnsweredComments.indexOfFirst { it.isDuplicateOf(review) }
            val duplicate by lazy { notAnsweredComments[duplicateIndex] }

            if (duplicateIndex == -1) {
                notAnsweredComments += review
            } else {
                notAnsweredComments -= duplicate
                result += zipReviews(review, duplicate)
            }
        }

        result += notAnsweredComments.map { it.toDetailedReviewDto() }

        return result
    }

    private fun zipReviews(review1: ReviewDto, review2: ReviewDto): DetailedReviewDto {
        val publisherReply = if (review1.isPublisherReply) review1 else review2
        val comment = if (review2.isPublisherReply) review1 else review2

        return comment.toDetailedReviewDto(publisherReply.comment, publisherReply.publishingDate)
    }

    private fun ReviewDto.isDuplicateOf(other: ReviewDto): Boolean {
        return this.assetShortUrl == other.assetShortUrl &&
                this.authorName == other.authorName
    }

    @SuppressLint("Range")
    private fun ReviewDto.toDetailedReviewDto(
        publisherReply: String? = null,
        publisherReplyDate: Date? = null
    ): DetailedReviewDto {
        return DetailedReviewDto(
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