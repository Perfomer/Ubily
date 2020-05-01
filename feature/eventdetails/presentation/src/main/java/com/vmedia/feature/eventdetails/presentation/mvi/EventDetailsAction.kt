package com.vmedia.feature.eventdetails.presentation.mvi

import com.vmedia.core.common.pure.obj.event.EventInfo

internal sealed class EventDetailsAction {

    object EventDetailsLoadingStarted : EventDetailsAction()

    class EventDetailsLoadingSucceed(val payload: EventInfo<*>) : EventDetailsAction()

    class EventDetailsLoadingFailed(val error: Throwable) : EventDetailsAction()

}