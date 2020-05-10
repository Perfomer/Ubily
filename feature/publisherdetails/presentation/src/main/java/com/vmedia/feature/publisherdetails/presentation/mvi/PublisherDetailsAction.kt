package com.vmedia.feature.publisherdetails.presentation.mvi

import com.vmedia.feature.publisherdetails.domain.model.PublisherDetails

internal sealed class PublisherDetailsAction {

    object PublisherLoadingStarted : PublisherDetailsAction()

    class PublisherLoadingSucceed(val payload: PublisherDetails) : PublisherDetailsAction()

    class PublisherLoadingFailed(val error: Throwable) : PublisherDetailsAction()

}