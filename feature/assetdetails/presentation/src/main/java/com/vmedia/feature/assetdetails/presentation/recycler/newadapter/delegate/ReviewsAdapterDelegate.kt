package com.vmedia.feature.assetdetails.presentation.recycler.newadapter.delegate

import androidx.core.view.isVisible
import com.vmedia.core.common.android.util.ArrayAdapters
import com.vmedia.core.common.android.util.diffedValue
import com.vmedia.core.common.android.util.disableTouches
import com.vmedia.core.common.android.util.reviewsSortTypeLabelResources
import com.vmedia.core.common.android.util.setOnClickListener
import com.vmedia.core.common.android.util.setOnItemSelectedListener
import com.vmedia.core.common.android.util.toSpan
import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.core.common.android.view.recycler.base.ViewHolderOnClickListener
import com.vmedia.core.common.android.view.recycler.base.adapterDelegateViewBinding
import com.vmedia.core.common.pure.obj.ReviewsSortType
import com.vmedia.core.common.pure.util.cropToString
import com.vmedia.feature.assetdetails.presentation.R
import com.vmedia.feature.assetdetails.presentation.databinding.AssetdetailsCardArtworksBinding
import com.vmedia.feature.assetdetails.presentation.databinding.AssetdetailsCardReviewsBinding
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent
import com.vmedia.feature.assetdetails.presentation.recycler.artwork.ArtworksAdapter
import com.vmedia.feature.assetdetails.presentation.recycler.newadapter.listitem.ArtworksListItem
import com.vmedia.feature.assetdetails.presentation.recycler.newadapter.listitem.ReviewsListItem
import com.vmedia.feature.assetdetails.presentation.recycler.review.OnAuthorClickListener
import com.vmedia.feature.assetdetails.presentation.recycler.review.ReviewsAdapter
import kotlinx.android.synthetic.main.assetdetails_card_reviews.*

internal typealias OnReviewsSortTypeChangedListener = (type: ReviewsSortType) -> Unit
internal typealias OnCollapsedReviewsClickListener = () -> Unit

internal fun reviewsAdapterDelegate(
    onAuthorClickListener: OnAuthorClickListener,
    onReviewsSortTypeChangedListener: OnReviewsSortTypeChangedListener,
    onCollapsedReviewsClickListener: OnCollapsedReviewsClickListener,
) = adapterDelegateViewBinding<ReviewsListItem, BaseListItem, AssetdetailsCardReviewsBinding>(
    AssetdetailsCardReviewsBinding::inflate
) {
    val adapter = ReviewsAdapter(onAuthorClickListener)

    binding.assetdetailsReviewsSortValue.adapter = ArrayAdapters.createFromResources(
        context,
        reviewsSortTypeLabelResources,
        R.layout.common_reviews_sort_item
    )
    binding.assetdetailsReviewsSortValue.setOnItemSelectedListener { index ->
        val selectedType = ReviewsSortType.values()[index]
        onReviewsSortTypeChangedListener.invoke(selectedType)
    }

    binding.assetdetailsReviewsScrim.setOnClickListener(onCollapsedReviewsClickListener)
    binding.assetdetailsReviewsViewmore.setOnClickListener(onCollapsedReviewsClickListener)

    binding.assetdetailsReviewsList.adapter = adapter

    binding.assetdetailsReviews1.disableTouches()
    binding.assetdetailsReviews2.disableTouches()
    binding.assetdetailsReviews3.disableTouches()
    binding.assetdetailsReviews4.disableTouches()
    binding.assetdetailsReviews5.disableTouches()

    bind {
        binding.assetdetailsReviewsSortValue.setSelection(item.reviewsSortType.ordinal)
        binding.assetdetailsReviewsCount.text = getString(R.string.reviews_count, item.reviews.reviewsCount).toSpan()

        binding.assetdetailsReviewsRatingValue.diffedValue = item.reviews.averageReviewsRating.cropToString()

        binding.assetdetailsReviews1Count.diffedValue = item.reviews.oneStarStats.count.toString()
        binding.assetdetailsReviews2Count.diffedValue = item.reviews.twoStarsStats.count.toString()
        binding.assetdetailsReviews3Count.diffedValue = item.reviews.threeStarsStats.count.toString()
        binding.assetdetailsReviews4Count.diffedValue = item.reviews.fourStarsStats.count.toString()
        binding.assetdetailsReviews5Count.diffedValue = item.reviews.fiveStarsStats.count.toString()

        binding.assetdetailsReviews1Progress.diffedValue = item.reviews.oneStarStats.percent
        binding.assetdetailsReviews2Progress.diffedValue = item.reviews.twoStarsStats.percent
        binding.assetdetailsReviews3Progress.diffedValue = item.reviews.threeStarsStats.percent
        binding.assetdetailsReviews4Progress.diffedValue = item.reviews.fourStarsStats.percent
        binding.assetdetailsReviews5Progress.diffedValue = item.reviews.fiveStarsStats.percent

        binding.assetdetailsReviewsViewmore.isVisible = !item.isReviewsExpanded
        binding.assetdetailsReviewsScrim.isVisible = !item.isReviewsExpanded

        adapter.setItems(item.reviews.reviews, item.isReviewsExpanded)
    }
}
