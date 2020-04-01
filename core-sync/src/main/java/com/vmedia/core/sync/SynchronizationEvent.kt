package com.vmedia.core.sync

import com.vmedia.core.common.obj.Period
import com.vmedia.core.data.internal.database.entity.*
import com.vmedia.core.sync.synchronizer.asset.AssetModel

sealed class SynchronizationEvent {

    abstract class Data<T> : SynchronizationEvent() {
        abstract val data: T
    }

    object Loading : SynchronizationEvent()
    object Cancelled : SynchronizationEvent()
    class Error(val error: Throwable) : SynchronizationEvent()

    class PublisherReceived(override val data: Publisher) : Data<Publisher>()
    class AssetsReceived(override val data: List<AssetModel>) : Data<List<AssetModel>>()
    class PeriodsReceived(override val data: List<Period>) : Data<List<Period>>()
    class UsersReceived(override val data: List<User>) : Data<List<User>>()
    class ReviewsReceived(override val data: List<Review>) : Data<List<Review>>()
    class SalesReceived(override val data: List<Sale>) : Data<List<Sale>>()
    class FreeDownloadsReceived(override val data: List<Sale>) : Data<List<Sale>>()
    class RevenuesReceived(override val data: List<Revenue>) : Data<List<Revenue>>()
    class PayoutsReceived(override val data: List<Payout>) : Data<List<Payout>>()

}

enum class SynchronizationDataType {
    PUBLISHER,
    ASSETS,
    PERIODS,
    USERS,
    REVIEWS,
    SALES,
    FREE_DOWNLOADS,
    REVENUES,
    PAYOUTS
}