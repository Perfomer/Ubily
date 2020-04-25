package com.vmedia.feature.assetdetails.presentation

import com.vmedia.core.common.mvi.MviViewModel
import com.vmedia.core.common.util.toObservable
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
    }

    private companion object {

        private const val MAX_COLLAPSED_DESCRIPTION_LENGTH = 300
        private const val MAX_COLLAPSED_KEYWORDS_COUNT = 10
        private const val MAX_COLLAPSED_REVIEWS_COUNT = 3

    }

}