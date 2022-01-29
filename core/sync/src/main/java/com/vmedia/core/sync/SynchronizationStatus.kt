package com.vmedia.core.sync

data class SynchronizationStatus(
    val isAuthFailed: Boolean = false,
    val events: Map<SynchronizationDataType, SynchronizationEvent> = emptyMap(),
) {

    val maxEventsCount: Int = SynchronizationDataType.values().size

    val isFinished: Boolean
        get() {
            val correctSize = events.size == maxEventsCount
            return correctSize && !events.containsValue(SynchronizationEvent.Loading)
        }

    val hasErrors: Boolean
        get() = events.values.find { it is SynchronizationEvent.Error } != null

    val readyEventsCount: Int
        get() = events.values.count { it is SynchronizationEvent.Data<*> }
}
