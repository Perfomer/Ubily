package com.vmedia.feature.assetdetails.presentation.mvi

import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.core.common.pure.obj.ReviewsSortType
import com.vmedia.core.common.pure.obj.ReviewsSortType.RELEVANCE
import com.vmedia.feature.assetdetails.domain.model.AssetDetails

internal data class AssetDetailsState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val content: List<BaseListItem> = emptyList(),
    val payload: AssetDetails = AssetDetails(),
    val reviewsSortType: ReviewsSortType = RELEVANCE,
    val isDescriptionExpanded: Boolean = false,
    val isReviewsExpanded: Boolean = false
)
