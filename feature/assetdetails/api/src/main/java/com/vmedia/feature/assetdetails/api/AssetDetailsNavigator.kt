package com.vmedia.feature.assetdetails.api

const val BEAN_FRAGMENT_ASSETDETAILS = "AssetDetailsFragment"

interface AssetDetailsNavigator {

    fun navigateToUrl(url: String)

    fun navigateToPublisher()

    fun navigateToUser(id: Long)

    fun navigateToAssetsSearch(keywordId: Long)

    fun navigateToGallery(
        images: List<String>,
        targetImagesPosition: Int = 0
    )

    fun navigateToGallery(imageUrl: String)

}