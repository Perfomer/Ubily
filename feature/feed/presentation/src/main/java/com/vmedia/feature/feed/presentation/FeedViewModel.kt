package com.vmedia.feature.feed.presentation

import com.vmedia.core.common.mvi.MviViewModel
import com.vmedia.core.domain.repository.EventRepository
import com.vmedia.feature.feed.presentation.mvi.FeedAction
import com.vmedia.feature.feed.presentation.mvi.FeedAction.*
import com.vmedia.feature.feed.presentation.mvi.FeedIntent
import com.vmedia.feature.feed.presentation.mvi.FeedIntent.ObserveEvents
import com.vmedia.feature.feed.presentation.mvi.FeedState

internal class FeedViewModel(
    private val repository: EventRepository
) : MviViewModel<FeedIntent, FeedAction, FeedState, Nothing>(
    initialState = FeedState()
) {

    override fun act(
        state: FeedState,
        intent: FeedIntent
    ) = when (intent) {
        ObserveEvents -> repository.getEvents()
            .asFlowSource(ObserveEvents::class)
            .map<FeedAction>(::EventsLoadingSucceed)
            .startWith(EventsLoadingStarted)
            .onErrorReturn(::EventsLoadingFailed)
    }

    override fun reduce(
        oldState: FeedState,
        action: FeedAction
    ) = when (action) {
        EventsLoadingStarted -> oldState.copy(isLoading = true, error = null)
        is EventsLoadingSucceed -> oldState.copy(isLoading = false, payload = action.events)
        is EventsLoadingFailed -> oldState.copy(isLoading = false, error = action.error)
    }

}