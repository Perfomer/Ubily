package com.vmedia.feature.feed.presentation.mvi

sealed class FeedIntent {

    object ObserveEvents : FeedIntent()

}