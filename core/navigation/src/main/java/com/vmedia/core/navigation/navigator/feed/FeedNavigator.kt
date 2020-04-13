package com.vmedia.core.navigation.navigator.feed

import com.vmedia.core.navigation.navigator.eventdetails.EventDetailsNavigator

interface FeedNavigator : EventDetailsNavigator {

    fun navigateToEventDetails(eventId: Long)

}