package com.vmedia.feature.assetdetails.presentation.mvi

import com.vmedia.core.common.android.obj.ReviewsSortType
import com.vmedia.feature.assetdetails.domain.model.AssetDetails

internal sealed class AssetDetailsAction {

    object AssetLoadingStarted : AssetDetailsAction()

    class AssetLoadingSucceed(val payload: AssetDetails) : AssetDetailsAction()

    class AssetLoadingFailed(val error: Throwable) : AssetDetailsAction()

    object DescriptionExpanded : AssetDetailsAction()

    object ReviewsExpanded : AssetDetailsAction()

    class SortTypeUpdated(val sortType: ReviewsSortType) : AssetDetailsAction()

}