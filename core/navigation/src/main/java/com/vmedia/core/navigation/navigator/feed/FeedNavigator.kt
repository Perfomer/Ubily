package com.vmedia.core.navigation.navigator.feed

interface FeedNavigator {

    fun navigateToEventDetails(eventId: Long)

    fun navigateToAsset(assetId: Long)

    fun navigateToStatistics(periodId: Long)

}