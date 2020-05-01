package com.vmedia.core.sync.synchronizer.review

import com.vmedia.core.common.pure.util.Mapper
import com.vmedia.core.data.internal.database.entity.CommentBody
import com.vmedia.core.data.internal.database.entity.Review
import com.vmedia.core.network.entity.DetailedReviewDto
import com.vmedia.core.sync._AssetProviderByUrl
import com.vmedia.core.sync._UserProviderByName

internal class ReviewMapper(
    private val assetProvider: _AssetProviderByUrl,
    private val userProvider: _UserProviderByName
) : Mapper<DetailedReviewDto, Review> {

    override fun map(from: DetailedReviewDto): Review {
        val publisherReply =
            if (from.publisherReply.isNullOrBlank()) null
            else CommentBody(from.publisherReply!!, from.publisherReplyDate!!)

        return Review(
            assetId = assetProvider.invoke(from.assetShortUrl).id,
            authorId = userProvider.invoke(from.authorName).id,
            title = from.title,
            rating = from.rating,
            comment = CommentBody(
                comment = from.comment,
                publishingDate = from.publishingDate
            ),
            publisherReply = publisherReply
        )
    }

}