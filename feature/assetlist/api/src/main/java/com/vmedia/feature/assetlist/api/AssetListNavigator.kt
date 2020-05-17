package com.vmedia.feature.assetlist.api

const val BEAN_FRAGMENT_ASSETLIST = "AssetListFragment"

interface AssetListNavigator {

    fun navigateToAsset(assetId: Long)

    fun navigateToAssetSearch()

    fun navigateToPublisher()

}