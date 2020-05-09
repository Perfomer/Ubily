package com.vmedia.core.navigation.navigator.assetdetails

interface AssetDetailsNavigator {

    fun navigateToUrl(url: String)

    fun navigateToPublisher()

    fun navigateToUser(id: Long)

    fun navigateToAssetsSearch(keywordId: Long)

    fun navigateToGallery(
        images: List<String>,
        targetImagesPosition: Int = 0
    )

    fun navigateToGallery(imageUrl: String) {
        navigateToGallery(listOf(imageUrl))
    }

}