package com.example.feature.feed.domain.mapper

import androidx.annotation.WorkerThread
import com.example.feature.feed.domain.FeedRepository
import com.vmedia.core.common.util.ObservableMapper
import com.vmedia.core.common.util.associateWith
import com.vmedia.core.common.util.sumByBigDecimal
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.internal.database.entity.Sale
import com.vmedia.core.data.obj.EventInfo.EventListInfo.EventSale
import com.vmedia.core.data.obj.SaleInfo
import io.reactivex.Observable

internal class SaleMapper(
    private val repository: FeedRepository
) : ObservableMapper<Event, EventSale> {

    override fun map(from: Event): Observable<EventSale> {
        return repository.getEventSales(from.id)
            .associateWith { repository.getAsset(it.assetId) }
            .map { createEventSale(from, it) }
            .toObservable()
    }

    @WorkerThread
    private fun createEventSale(event: Event, detailedSales: List<Pair<Sale, Asset>>): EventSale {
        val salesInfo = detailedSales.groupBy { it.first.assetId }
            .values
            .map { it.map { it.first } to it[0].second }
            .map { (sales, asset) -> createSaleInfo(asset, sales) }

        return EventSale(
            id = event.id,
            date = event.date,
            content = salesInfo
        )
    }

    @WorkerThread
    private fun createSaleInfo(asset: Asset, sales: List<Sale>): SaleInfo {
        return SaleInfo(
            assetId = asset.id,
            assetName = asset.name,
            assetIcon = asset.iconImage,
            quantity = sales.sumBy(Sale::quantity),
            summaryPrice = sales.sumByBigDecimal(Sale::amount)
        )
    }

}