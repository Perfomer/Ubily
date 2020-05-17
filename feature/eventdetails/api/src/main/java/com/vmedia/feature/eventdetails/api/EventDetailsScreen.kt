package com.vmedia.feature.eventdetails.api

import com.vmedia.core.navigation.ScreenDestination.ScreenDestinationNew

class EventDetailsScreen(eventId: Long) : ScreenDestinationNew(
    BEAN_FRAGMENT_EVENTDETAILS,
    eventId
)