package com.vmedia.core.navigation.navigator.assetdetails

import com.vmedia.core.data.internal.database.entity.Artwork

interface AssetDetailsNavigator {

    fun navigateToUrl(url: String)

    fun navigateToPublisher()

    fun navigateToUser(id: Long)

    fun navigateToAssetsSearch(keywordId: Long)

    fun navigateToGallery(
        artworks: List<Artwork>,
        targetArtworkPosition: Int = 0
    )

}