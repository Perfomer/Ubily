package com.vmedia.feature.feed.domain.mapper

import androidx.annotation.WorkerThread
import com.vmedia.core.common.util.ObservableMapper
import com.vmedia.core.common.util.associateWith
import com.vmedia.core.common.util.sumByBigDecimal
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.internal.database.entity.Sale
import com.vmedia.core.data.obj.EventInfo
import com.vmedia.core.data.obj.SaleInfo
import com.vmedia.feature.feed.domain.FeedRepository
import io.reactivex.Observable

internal abstract class ProvisionMapper<T : EventInfo<*>>(
    private val repository: FeedRepository
) : ObservableMapper<Event, T> {

    override fun map(from: Event): Observable<T> {
        return repository.getEventSales(from.id)
            .associateWith { repository.getAsset(it.assetId) }
            .map(::createContent)
            .map { createEventInfo(from, it) }
            .toObservable()
    }

    @WorkerThread
    protected abstract fun createEventInfo(
        event: Event,
        content: List<SaleInfo>
    ): T

    @WorkerThread
    private fun createContent(detailedSales: List<Pair<Sale, Asset>>): List<SaleInfo> {
        return detailedSales.groupBy { it.first.assetId }
            .values
            .map { it.map(Pair<Sale, Asset>::first) to it[0].second }
            .map { (sales, asset) -> createSaleInfo(asset, sales) }
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