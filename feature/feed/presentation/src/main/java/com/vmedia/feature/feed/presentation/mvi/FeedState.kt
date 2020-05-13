package com.vmedia.feature.feed.presentation.mvi

import com.vmedia.core.common.pure.obj.event.EventInfo

internal data class FeedState(
    val isLoading: Boolean = false,
    val payload: List<EventInfo<*>> = emptyList(),
    val error: Throwable? = null
)