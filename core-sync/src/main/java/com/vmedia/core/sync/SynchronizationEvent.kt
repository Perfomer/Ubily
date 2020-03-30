package com.vmedia.core.sync

import com.vmedia.core.common.obj.Period
import com.vmedia.core.data.internal.database.entity.*
import com.vmedia.core.sync.synchronizer.asset.AssetModel

sealed class SynchronizationEvent {

    object Loading : SynchronizationEvent()
    object Cancelled : SynchronizationEvent()
    class Error(val error: Throwable) : SynchronizationEvent()

    class PublisherReceived(val item: Publisher) : SynchronizationEvent()
    class AssetsReceived(val items: List<AssetModel>) : SynchronizationEvent()
    class PeriodsReceived(val items: List<Period>) : SynchronizationEvent()
    class UsersReceived(val items: List<User>) : SynchronizationEvent()
    class ReviewsReceived(val items: List<Review>) : SynchronizationEvent()
    class SalesReceived(val items: List<Sale>) : SynchronizationEvent()
    class FreeDownloadsReceived(val items: List<Sale>) : SynchronizationEvent()
    class RevenuesReceived(val items: List<Revenue>) : SynchronizationEvent()
    class PayoutsReceived(val items: List<Payout>) : SynchronizationEvent()

}

enum class SynchronizationEventType {
    PUBLISHER_RECEIVED,
    ASSETS_RECEIVED,
    PERIODS_RECEIVED,
    USERS_RECEIVED,
    REVIEWS_RECEIVED,
    SALES_RECEIVED,
    FREE_DOWNLOADS_RECEIVED,
    REVENUES_RECEIVED,
    PAYOUTS_RECEIVED
}