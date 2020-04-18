package com.vmedia.feature.assetlist.presentation.mvi

import com.vmedia.feature.assetlist.domain.model.AssetShortInfo

internal sealed class AssetListAction {

    object AssetsLoadingStarted : AssetListAction()

    class AssetsLoadingSucceed(
        val payload: List<AssetShortInfo>,
        val publisherAvatar: String
    ) : AssetListAction()

    class AssetsLoadingFailed(val error: Throwable) : AssetListAction()

}