package com.vmedia.feature.publisherdetails.presentation.mvi

import com.vmedia.feature.publisherdetails.domain.model.PublisherDetails

internal data class PublisherDetailsState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val payload: PublisherDetails = PublisherDetails()
)