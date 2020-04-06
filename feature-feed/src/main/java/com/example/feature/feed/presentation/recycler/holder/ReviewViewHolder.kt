package com.example.feature.feed.presentation.recycler.holder

import android.view.View
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.example.feature.feed.presentation.recycler.FeedViewHolder
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.data.obj.EventInfo.EventReview
import kotlinx.android.synthetic.main.feed_item_review.*

internal class ReviewViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit,
    onAssetClick: (position: Int) -> Unit
) : FeedViewHolder<EventReview>(containerView, onClick) {

    init {
        feed_item_review_asset.setOnClickListener { onAssetClick.safeInvoke(adapterPosition) }
    }

    override fun bindContent(item: EventReview) {
        val review = item.review
        feed_item_review_title.diffedValue = review.reviewTitle
        feed_item_review_body.diffedValue = review.reviewBody
        feed_item_review_rating.rating = review.rating.toFloat()

        feed_item_review_asset_name.diffedValue = review.assetName
        feed_item_review_asset_rating.diffedValue = review.assetAverageRating.toString()

        feed_item_review_asset_icon.load(review.assetIcon) {
            crossfade(true)
            transformations(RoundedCornersTransformation(4.0f))
        }
    }

}