package com.vmedia.core.sync

import com.vmedia.core.sync.SynchronizationEvent.Loading

data class SynchronizationStatus(
    val events: Map<SynchronizationEventType, SynchronizationEvent>
) {

    val isFinished: Boolean
        get() {
            val correctSize = events.size == SynchronizationEventType.values().size
            return correctSize && !events.containsValue(Loading)
        }

}