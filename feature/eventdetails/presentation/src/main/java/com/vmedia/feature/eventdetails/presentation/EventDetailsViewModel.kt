package com.vmedia.feature.eventdetails.presentation

import com.vmedia.core.common.android.mvi.MviViewModel
import com.vmedia.core.domain.repository.EventRepository
import com.vmedia.feature.eventdetails.presentation.mvi.EventDetailsAction
import com.vmedia.feature.eventdetails.presentation.mvi.EventDetailsAction.*
import com.vmedia.feature.eventdetails.presentation.mvi.EventDetailsIntent
import com.vmedia.feature.eventdetails.presentation.mvi.EventDetailsIntent.LoadEventDetails
import com.vmedia.feature.eventdetails.presentation.mvi.EventDetailsState

internal class EventDetailsViewModel(
    private val eventId: Long,
    private val repository: EventRepository
) : MviViewModel<EventDetailsIntent, EventDetailsAction, EventDetailsState, Nothing>(
    initialState = EventDetailsState()
) {

    override fun act(
        state: EventDetailsState,
        intent: EventDetailsIntent
    ) = when (intent) {
        LoadEventDetails -> repository.getEvent(eventId)
            .map<EventDetailsAction>(::EventDetailsLoadingSucceed)
            .startWith(EventDetailsLoadingStarted)
            .onErrorReturn(::EventDetailsLoadingFailed)
    }

    override fun reduce(
        oldState: EventDetailsState,
        action: EventDetailsAction
    ) = when (action) {
        EventDetailsLoadingStarted -> oldState.copy(isLoading = true, error = null)
        is EventDetailsLoadingSucceed -> oldState.copy(isLoading = false, payload = action.payload)
        is EventDetailsLoadingFailed -> oldState.copy(isLoading = false, error = action.error)
    }

}