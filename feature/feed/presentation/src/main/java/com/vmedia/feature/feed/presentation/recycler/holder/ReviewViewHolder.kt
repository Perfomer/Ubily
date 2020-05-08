package com.vmedia.feature.feed.presentation.recycler.holder

import android.view.View
import androidx.core.view.isVisible
import com.vmedia.core.common.android.util.diffedValue
import com.vmedia.core.common.android.util.disableTouches
import com.vmedia.core.common.android.util.loadRoundedImage
import com.vmedia.core.common.pure.obj.event.EventInfo.EventReview
import com.vmedia.feature.feed.presentation.R
import com.vmedia.feature.feed.presentation.recycler.FeedViewHolder
import kotlinx.android.synthetic.main.feed_item_review.*

internal class ReviewViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit,
    onOptionsClick: (position: Int) -> Unit,
    onAssetClick: (position: Int) -> Unit
) : FeedViewHolder<EventReview>(containerView, onClick, onOptionsClick) {

    init {
        feed_item_review_asset.setOnClickListener { onAssetClick.invokeWithPosition() }
        feed_item_review_rating.max = 5
        feed_item_review_rating.disableTouches()
    }

    override fun bindContent(item: EventReview) {
        val review = item.content
        val hasReply = review.publisherReplyBody != null

        feed_item_review_rating.rating = review.rating.toFloat()
        feed_item_review_title.diffedValue = review.reviewTitle
        feed_item_review_body.diffedValue = review.reviewBody

        feed_item_review_asset_name.diffedValue = review.assetName
        feed_item_review_asset_rating.diffedValue = review.assetAverageRating.toString()

        feed_item_review_reply.isVisible = hasReply

        if (hasReply) {
            feed_item_review_reply_body.diffedValue = review.publisherReplyBody!!
        }

        feed_item_review_asset_icon.loadRoundedImage(
            imageUrl = review.assetIcon,
            cornerRadius = R.dimen.asset_icon_corners_radius_small,
            placeholder = R.drawable.bg_placeholder_rect_rounded_small
        )
    }

}