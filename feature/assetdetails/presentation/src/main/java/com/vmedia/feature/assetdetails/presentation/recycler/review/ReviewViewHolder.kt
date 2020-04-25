package com.vmedia.feature.assetdetails.presentation.recycler.review

import android.view.View
import androidx.core.view.isVisible
import com.vmedia.core.common.util.FORMAT_REVIEWS
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.format
import com.vmedia.core.common.view.recycler.base.BaseViewHolder
import com.vmedia.core.data.internal.database.model.ReviewDetailed
import kotlinx.android.synthetic.main.assetdetails_review_item.*

internal class ReviewViewHolder(
    containerView: View,
    onAuthorClick: (position: Int) -> Unit
) : BaseViewHolder(containerView) {

    init {
        assetdetails_review_item_avatar.setOnClickListener {
            onAuthorClick.invokeWithPosition()
        }
    }

    fun bind(review: ReviewDetailed) {
        val authorName = review.authorName
        val publisherReply = review.publisherReply
        val hasReply = !review.publisherReply.isNullOrBlank()

        assetdetails_review_item_avatar_letter.diffedValue = authorName
        assetdetails_review_item_author.diffedValue = authorName
        assetdetails_review_item_date.diffedValue = review.publishingDate.format(FORMAT_REVIEWS)
        assetdetails_review_item_rating.diffedValue = review.rating * 2
        assetdetails_review_item_title.diffedValue = review.title
        assetdetails_review_item_comment.diffedValue = review.comment

        assetdetails_review_item_reply_comment.isVisible = hasReply
        assetdetails_review_item_reply_title.isVisible = hasReply

        if (hasReply) {
            assetdetails_review_item_reply_comment.diffedValue = publisherReply!!
        }
    }

}