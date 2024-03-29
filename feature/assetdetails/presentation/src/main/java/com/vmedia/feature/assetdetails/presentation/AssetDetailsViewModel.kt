package com.vmedia.feature.assetdetails.presentation

import com.vmedia.core.common.android.mvi.MviViewModel
import com.vmedia.core.common.pure.obj.ReviewsSortType
import com.vmedia.core.common.pure.util.rx.toObservable
import com.vmedia.core.data.internal.database.model.ReviewDetailed
import com.vmedia.feature.assetdetails.domain.AssetDetailsInteractor
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsAction
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsAction.*
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent.*
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsState
import com.vmedia.feature.assetdetails.presentation.recycler.AssetDetailsListItemFactory

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
            val reviewsSortType = oldState.reviewsSortType

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
                        reviews = sort(payload.reviews.reviews, reviewsSortType)
                    )
                ),
                content = AssetDetailsListItemFactory.create(
                    assetDetails = payload,
                    reviewsSortType = reviewsSortType,
                    isReviewsExpanded = isReviewsExpanded,
                    isDescriptionExpanded = isDescriptionExpanded,
                ),
                isDescriptionExpanded = isDescriptionExpanded,
                isReviewsExpanded = isReviewsExpanded
            )
        }

        is AssetLoadingFailed -> {
            oldState.copy(isLoading = false, error = action.error)
        }

        DescriptionExpanded -> {
            oldState.copy(
                content = AssetDetailsListItemFactory.create(
                    assetDetails = oldState.payload,
                    reviewsSortType = oldState.reviewsSortType,
                    isReviewsExpanded = oldState.isReviewsExpanded,
                    isDescriptionExpanded = true,
                ),
                isDescriptionExpanded = true,
            )
        }

        ReviewsExpanded -> {
            oldState.copy(
                content = AssetDetailsListItemFactory.create(
                    assetDetails = oldState.payload,
                    reviewsSortType = oldState.reviewsSortType,
                    isReviewsExpanded = true,
                    isDescriptionExpanded = oldState.isDescriptionExpanded,
                ),
                isReviewsExpanded = true,
            )
        }

        is SortTypeUpdated -> {
            val reviewsSortType = action.sortType
            val sortedReviews = sort(oldState.payload.reviews.reviews, reviewsSortType)
            val payload = oldState.payload.copy(
                reviews = oldState.payload.reviews.copy(
                    reviews = sortedReviews
                )
            )

            oldState.copy(
                reviewsSortType = reviewsSortType,
                content = AssetDetailsListItemFactory.create(
                    assetDetails = payload,
                    reviewsSortType = reviewsSortType,
                    isReviewsExpanded = oldState.isReviewsExpanded,
                    isDescriptionExpanded = oldState.isDescriptionExpanded,
                ),
                payload = payload
            )
        }
    }

    internal companion object {

        internal const val MAX_COLLAPSED_REVIEWS_COUNT = 3

        private const val MAX_COLLAPSED_DESCRIPTION_LENGTH = 300
        private const val MAX_COLLAPSED_KEYWORDS_COUNT = 10

        private fun sort(
            reviews: List<ReviewDetailed>,
            sortType: ReviewsSortType
        ): List<ReviewDetailed> {
            return when (sortType) {
                ReviewsSortType.RELEVANCE -> {
                    reviews.sortedWith(
                        compareBy<ReviewDetailed> { it.comment.isBlank() }
                            .thenByDescending(ReviewDetailed::publishingDate)
                    )
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
