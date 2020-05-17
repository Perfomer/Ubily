package com.vmedia.feature.feed.api

const val BEAN_FRAGMENT_FEED = "FeedFragment"

interface FeedNavigator {

    fun navigateToEventDetails(eventId: Long)

    fun navigateToAsset(assetId: Long)

    fun navigateToStatistics(periodId: Long)

}