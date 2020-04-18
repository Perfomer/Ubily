package com.vmedia.feature.assetlist.presentation.mvi

internal sealed class AssetListIntent {

    object LoadData : AssetListIntent()

}