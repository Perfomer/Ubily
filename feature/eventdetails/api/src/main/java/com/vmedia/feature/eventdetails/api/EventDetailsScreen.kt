package com.vmedia.feature.eventdetails.api

import com.vmedia.core.navigation.ScreenDestination

class EventDetailsScreen(eventId: Long) : ScreenDestination(
    BEAN_FRAGMENT_EVENTDETAILS,
    eventId
)