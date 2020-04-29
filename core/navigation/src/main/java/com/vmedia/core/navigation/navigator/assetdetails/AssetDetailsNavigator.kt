package com.vmedia.core.navigation.navigator.assetdetails

interface AssetDetailsNavigator {

    fun navigateToUrl(url: String)

    fun navigateToPublisher()

    fun navigateToUser(id: Long)

    fun navigateToAssetsSearch(keywordId: Long)

    fun navigateToGallery(
        artworks: List<String>,
        targetArtworkPosition: Int = 0
    )

}