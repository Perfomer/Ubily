package com.vmedia.core.sync

sealed class SynchronizationEvent {

    class Data<T>(val data: T) : SynchronizationEvent()
    object Loading : SynchronizationEvent()
    object Cancelled : SynchronizationEvent()
    class Error(val error: Throwable) : SynchronizationEvent()

}

enum class SynchronizationDataType {
    PUBLISHER,
    ASSETS_CATEGORIES,
    ASSETS,
    PERIODS,
    USERS,
    REVIEWS,
    SALES,
    FREE_DOWNLOADS,
    REVENUES,
    PAYOUTS
}