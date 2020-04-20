package com.vmedia.feature.assetdetails.presentation.mvi

import com.vmedia.feature.assetdetails.domain.model.AssetDetails

internal sealed class AssetDetailsAction {

    object AssetLoadingStarted : AssetDetailsAction()

    class AssetLoadingSucceed(val payload: AssetDetails) : AssetDetailsAction()

    class AssetLoadingFailed(val error: Throwable) : AssetDetailsAction()

}