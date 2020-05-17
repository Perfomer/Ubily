package com.vmedia.feature.eventdetails.api

const val BEAN_FRAGMENT_EVENTDETAILS = "EventDetailsFragment"

interface EventDetailsNavigator {

    fun navigateToAsset(assetId: Long)

    fun navigateToStatistics(periodId: Long)

}