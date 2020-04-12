package com.vmedia.core.sync

import com.vmedia.core.sync.SynchronizationEvent.Loading

data class SynchronizationStatus(
    val events: Map<SynchronizationDataType, SynchronizationEvent> = emptyMap()
) {

    val isFinished: Boolean
        get() {
            val correctSize = events.size == SynchronizationDataType.values().size
            return correctSize && !events.containsValue(Loading)
        }

    val hasErrors: Boolean
        get() = events.values.find { it is SynchronizationEvent.Error } != null

}