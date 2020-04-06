package com.example.feature.feed.presentation.mvi

sealed class FeedIntent {

    object ObserveEvents : FeedIntent()

}