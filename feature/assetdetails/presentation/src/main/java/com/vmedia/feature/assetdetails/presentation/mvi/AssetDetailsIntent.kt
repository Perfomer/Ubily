package com.vmedia.feature.assetdetails.presentation.mvi

internal sealed class AssetDetailsIntent {

    object LoadData : AssetDetailsIntent()

    object ExpandDescription : AssetDetailsIntent()



}