package com.vmedia.feature.feed.presentation.recycler.holder

import android.view.View
import androidx.core.view.isVisible
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.loadRoundedCorners
import com.vmedia.core.common.util.toSpan
import com.vmedia.core.data.obj.EventInfo.EventReview
import com.vmedia.feature.feed.R
import com.vmedia.feature.feed.presentation.recycler.FeedViewHolder
import kotlinx.android.synthetic.main.feed_item.*
import kotlinx.android.synthetic.main.feed_item_review.*

internal class ReviewViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit,
    onOptionsClick: (position: Int) -> Unit,
    onAssetClick: (position: Int) -> Unit
) : FeedViewHolder<EventReview>(containerView, onClick, onOptionsClick) {

    init {
        feed_item_review_asset.setOnClickListener { onAssetClick.safeInvoke(adapterPosition) }
    }

    override fun bindContent(item: EventReview) {
        val review = item.content
        val hasReply = review.publisherReplyBody != null

        feed_item_description.text = context.getString(
            R.string.event_review_text,
            review.authorName
        ).toSpan()

        feed_item_review_rating.rating = review.rating.toFloat()
        feed_item_review_title.diffedValue = review.reviewTitle
        feed_item_review_body.diffedValue = review.reviewBody

        feed_item_review_asset_name.diffedValue = review.assetName
        feed_item_review_asset_rating.diffedValue = review.assetAverageRating.toString()

        feed_item_review_reply.isVisible = hasReply

        if (hasReply) {
            feed_item_review_reply_body.diffedValue = review.publisherReplyBody!!
        }

        feed_item_review_asset_icon.loadRoundedCorners(
            imageUrl = review.assetIcon,
            cornerRadius = R.dimen.asset_icon_corners_radius_small
        )
    }

}