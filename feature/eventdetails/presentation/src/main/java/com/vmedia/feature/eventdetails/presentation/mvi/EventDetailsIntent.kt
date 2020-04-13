package com.vmedia.feature.eventdetails.presentation.mvi

internal sealed class EventDetailsIntent {

    object LoadEventDetails : EventDetailsIntent()

}