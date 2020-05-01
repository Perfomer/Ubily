package com.vmedia.feature.assetdetails.presentation.mvi

import com.vmedia.core.common.android.obj.ReviewsSortType

internal sealed class AssetDetailsIntent {

    object LoadData : AssetDetailsIntent()

    object ExpandDescription : AssetDetailsIntent()

    object ExpandReviews : AssetDetailsIntent()

    class UpdateSortType(val sortType: ReviewsSortType) : AssetDetailsIntent()

}