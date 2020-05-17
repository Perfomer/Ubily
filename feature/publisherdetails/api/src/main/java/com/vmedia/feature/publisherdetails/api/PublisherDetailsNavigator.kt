package com.vmedia.feature.publisherdetails.api

const val BEAN_FRAGMENT_PUBLISHERDETAILS = "PublisherDetailsFragment"

interface PublisherDetailsNavigator {

    fun navigateToUrl(url: String)

    fun navigateToGallery(imageUrl: String)

}