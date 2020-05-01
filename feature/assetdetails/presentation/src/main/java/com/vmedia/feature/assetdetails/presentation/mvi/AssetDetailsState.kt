package com.vmedia.feature.assetdetails.presentation.mvi

import com.vmedia.core.common.android.obj.ReviewsSortType
import com.vmedia.core.common.android.obj.ReviewsSortType.RELEVANCE
import com.vmedia.feature.assetdetails.domain.model.AssetDetails

internal data class AssetDetailsState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val payload: AssetDetails = AssetDetails(),
    val reviewsSortType: ReviewsSortType = RELEVANCE,
    val isDescriptionExpanded: Boolean = false,
    val isReviewsExpanded: Boolean = false
)