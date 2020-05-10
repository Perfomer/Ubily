package com.vmedia.feature.publisherdetails.presentation

import com.vmedia.core.common.android.mvi.MviViewModel
import com.vmedia.feature.publisherdetails.domain.PublisherDetailsInteractor
import com.vmedia.feature.publisherdetails.presentation.mvi.PublisherDetailsAction
import com.vmedia.feature.publisherdetails.presentation.mvi.PublisherDetailsAction.*
import com.vmedia.feature.publisherdetails.presentation.mvi.PublisherDetailsIntent
import com.vmedia.feature.publisherdetails.presentation.mvi.PublisherDetailsIntent.LoadData
import com.vmedia.feature.publisherdetails.presentation.mvi.PublisherDetailsState

internal class PublisherDetailsViewModel(
    private val interactor: PublisherDetailsInteractor
) : MviViewModel<PublisherDetailsIntent, PublisherDetailsAction, PublisherDetailsState, Nothing>(
    initialState = PublisherDetailsState()
) {

    override fun act(
        state: PublisherDetailsState,
        intent: PublisherDetailsIntent
    ) = when (intent) {
        LoadData -> interactor.getPublisher()
            .asFlowSource(LoadData::class)
            .map<PublisherDetailsAction>(::PublisherLoadingSucceed)
            .startWith(PublisherLoadingStarted)
            .onErrorReturn(::PublisherLoadingFailed)
    }

    override fun reduce(
        oldState: PublisherDetailsState,
        action: PublisherDetailsAction
    ) = when (action) {
        PublisherLoadingStarted -> oldState.copy(isLoading = true, error = null)
        is PublisherLoadingSucceed -> oldState.copy(isLoading = false, payload = action.payload)
        is PublisherLoadingFailed -> oldState.copy(isLoading = false, error = action.error)
    }

}