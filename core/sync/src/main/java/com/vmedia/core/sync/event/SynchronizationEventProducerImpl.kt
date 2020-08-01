package com.vmedia.core.sync.event

import android.annotation.SuppressLint
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Payout
import com.vmedia.core.data.internal.database.entity.Revenue
import com.vmedia.core.data.internal.database.entity.Review
import com.vmedia.core.data.internal.database.entity.Sale
import com.vmedia.core.sync.*
import com.vmedia.core.sync.SynchronizationDataType.*
import com.vmedia.core.sync.SynchronizationEvent.Data
import com.vmedia.core.sync.synchronizer.asset.AssetModel
import io.reactivex.Completable

@SuppressLint("CheckResult")
internal class SynchronizationEventProducerImpl(
    private val databaseDataSource: DatabaseDataSource,

    private val assetEventExtractor: _AssetEventExtractor,
    private val saleEventExtractor: _SaleEventExtractor,
    private val downloadEventExtractor: _DownloadEventExtractor,
    private val reviewEventExtractor: _ReviewEventExtractor,
    private val revenueEventExtractor: _RevenueEventExtractor,
    private val payoutEventExtractor: _PayoutEventExtractor
) : SynchronizationEventProducer {

    @Suppress("UNCHECKED_CAST")
    override fun produce(status: SynchronizationStatus): Completable {
        if (!status.isFinished || status.hasErrors) {
            return Completable.complete()
        }

        val events = status.events

        val assets = events[ASSETS] as Data<List<AssetModel>>
        val reviews = events[REVIEWS] as Data<List<Review>>
        val revenues = events[REVENUES] as Data<List<Revenue>>
        val payouts = events[PAYOUTS] as Data<List<Payout>>
        val downloads = events[FREE_DOWNLOADS] as Data<List<Sale>>
        val sales = events[SALES] as Data<List<Sale>>

        val (freeSales, paidSales) = sales.data.partition(Sale::isFree)

        return Completable.mergeArray(
            assetEventExtractor.execute(assets.data),
            saleEventExtractor.execute(paidSales),
            downloadEventExtractor.execute(freeSales + downloads.data),
            reviewEventExtractor.execute(reviews.data),
            revenueEventExtractor.execute(revenues.data),
            payoutEventExtractor.execute(payouts.data)
        )
    }

    private fun <T> EventExtractor<T>.execute(source: T): Completable {
        return extract(source)
            .flatMapCompletable(::saveEvents)
    }

    private fun saveEvents(events: List<EventModel>): Completable {
        return Completable.defer {
            val eventSaveCompletables = events.map(::saveEvent).toTypedArray()
            Completable.mergeArray(*eventSaveCompletables)
        }
    }

    private fun saveEvent(event: EventModel): Completable {
        return databaseDataSource.putEvent(
            type = event.type,
            date = event.date,
            entityIds = event.entities
        )
    }

}