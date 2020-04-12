package com.vmedia.feature.feed.presentation.mvi

import com.vmedia.core.data.obj.EventInfo

internal sealed class FeedAction {

    object EventsLoadingStarted : FeedAction()

    class EventsLoadingSucceed(val events: List<EventInfo<*>>) : FeedAction()

    class EventsLoadingFailed(val error: Throwable) : FeedAction()

}