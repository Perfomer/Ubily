package com.vmedia.feature.assetdetails.presentation

import com.vmedia.core.common.mvi.MviViewModel
import com.vmedia.core.common.obj.ReviewsSortType
import com.vmedia.core.common.util.toObservable
import com.vmedia.core.data.internal.database.model.ReviewDetailed
import com.vmedia.feature.assetdetails.domain.AssetDetailsInteractor
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsAction
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsAction.*
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent.*
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsState

internal class AssetDetailsViewModel(
    private val assetId: Long,
    private val interactor: AssetDetailsInteractor
) : MviViewModel<AssetDetailsIntent, AssetDetailsAction, AssetDetailsState, Nothing>(
    initialState = AssetDetailsState()
) {

    override fun act(
        state: AssetDetailsState,
        intent: AssetDetailsIntent
    ) = when (intent) {
        LoadData -> interactor.getAsset(assetId)
            .asFlowSource(LoadData::class)
            .map<AssetDetailsAction>(::AssetLoadingSucceed)
            .startWith(AssetLoadingStarted)
            .onErrorReturn(::AssetLoadingFailed)

        ExpandDescription -> DescriptionExpanded.toObservable()

        ExpandReviews -> ReviewsExpanded.toObservable()

        is UpdateSortType -> SortTypeUpdated(intent.sortType).toObservable()
    }

    override fun reduce(
        oldState: AssetDetailsState,
        action: AssetDetailsAction
    ) = when (action) {
        AssetLoadingStarted -> {
            oldState.copy(isLoading = true, error = null)
        }

        is AssetLoadingSucceed -> {
            val payload = action.payload
            val asset = payload.asset

            val isDescriptionShort = asset.description.length <= MAX_COLLAPSED_DESCRIPTION_LENGTH
            val isKeywordsListShort = asset.keywords.size <= MAX_COLLAPSED_KEYWORDS_COUNT

            val isDescriptionExpanded =
                if (oldState.isDescriptionExpanded) true
                else isDescriptionShort && isKeywordsListShort

            val isReviewsExpanded =
                if (oldState.isReviewsExpanded) true
                else payload.reviews.reviewsCount <= MAX_COLLAPSED_REVIEWS_COUNT

            oldState.copy(
                isLoading = false,
                payload = payload.copy(
                    reviews = payload.reviews.copy(
                        collapsedReviews = payload.reviews.reviews.take(MAX_COLLAPSED_REVIEWS_COUNT)
                    )
                ),
                isDescriptionExpanded = isDescriptionExpanded,
                isReviewsExpanded = isReviewsExpanded
            )
        }

        is AssetLoadingFailed -> {
            oldState.copy(isLoading = false, error = action.error)
        }

        DescriptionExpanded -> {
            oldState.copy(isDescriptionExpanded = true)
        }

        ReviewsExpanded -> {
            oldState.copy(isReviewsExpanded = true)
        }

        is SortTypeUpdated -> {
            val reviewsSortType = action.sortType
            val sortedReviews = sort(oldState.payload.reviews.reviews, reviewsSortType)

            oldState.copy(
                reviewsSortType = reviewsSortType,
                payload = oldState.payload.copy(
                    reviews = oldState.payload.reviews.copy(
                        reviews = sortedReviews,
                        collapsedReviews = sortedReviews.take(MAX_COLLAPSED_REVIEWS_COUNT)
                    )
                )
            )
        }
    }

    private companion object {

        private const val MAX_COLLAPSED_DESCRIPTION_LENGTH = 300
        private const val MAX_COLLAPSED_KEYWORDS_COUNT = 10
        private const val MAX_COLLAPSED_REVIEWS_COUNT = 3

        fun sort(reviews: List<ReviewDetailed>, sortType: ReviewsSortType): List<ReviewDetailed> {
            return when (sortType) {
                ReviewsSortType.RELEVANCE -> {
                    reviews.sortedByDescending(ReviewDetailed::publishingDate)
                        .sortedBy { it.comment.isEmpty() }
                }

                ReviewsSortType.DATE_ASC -> {
                    reviews.sortedBy(ReviewDetailed::publishingDate)
                }

                ReviewsSortType.DATE_DESC -> {
                    reviews.sortedByDescending(ReviewDetailed::publishingDate)
                }

                ReviewsSortType.RATING_ASC -> {
                    reviews.sortedBy(ReviewDetailed::rating)
                }

                ReviewsSortType.RATING_DESC -> {
                    reviews.sortedByDescending(ReviewDetailed::rating)
                }
            }
        }

    }

}