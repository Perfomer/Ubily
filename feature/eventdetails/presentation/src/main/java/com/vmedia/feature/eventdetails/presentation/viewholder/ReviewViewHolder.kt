package com.vmedia.feature.eventdetails.presentation.viewholder

import android.content.Context
import androidx.core.view.isVisible
import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.event.ReviewInfo
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.loadImageWithRoundedCorners
import com.vmedia.feature.eventdetails.presentation.R
import kotlinx.android.synthetic.main.eventdetails_item_review.*

internal class ReviewViewHolder(
    context: Context,
    onAssetClick: (assetId: Long) -> Unit
) : EventDetailsViewHolder<ReviewInfo>(
    EventType.REVIEW,
    context,
    R.layout.eventdetails_item_review
) {

    init {
        eventdetails_item_review_asset.setOnClickListener {
            currentItem?.assetId?.let(onAssetClick::invoke)
        }
    }

    override fun bind(item: ReviewInfo) {
        val hasReply = item.publisherReplyBody != null

        eventdetails_item_review_rating.rating = item.rating.toFloat()
        eventdetails_item_review_title.diffedValue = item.reviewTitle
        eventdetails_item_review_body.diffedValue = item.reviewBody

        eventdetails_item_review_asset_name.diffedValue = item.assetName
        eventdetails_item_review_asset_rating.diffedValue = item.assetAverageRating.toString()

        eventdetails_item_review_reply.isVisible = hasReply

        if (hasReply) {
            eventdetails_item_review_reply_body.diffedValue = item.publisherReplyBody!!
        }

        eventdetails_item_review_asset_icon.loadImageWithRoundedCorners(
            imageUrl = item.assetIcon,
            cornerRadius = R.dimen.asset_icon_corners_radius_small
        )
    }

}