package com.vmedia.core.sync.event.producer

import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.toPeriod
import com.vmedia.core.common.util.maxValue
import com.vmedia.core.data.internal.database.entity.Sale
import com.vmedia.core.sync._SaleIdProvider
import com.vmedia.core.sync.event.EventExtractor
import com.vmedia.core.sync.event.EventModel
import io.reactivex.Single
import java.util.*

internal abstract class ProvisionEventExtractor(
    private val eventType: EventType,
    private val saleIdProvider: _SaleIdProvider
) : EventExtractor<List<Sale>> {

    override fun extract(source: List<Sale>): Single<List<EventModel>> {
        return Single.fromCallable {
            source.groupBy { it.date.toPeriod() }
                .values
                .map { sales ->
                    val date = sales.maxValue(Sale::date)
                    val ids = sales.map { sale ->
                        saleIdProvider.invoke(sale.date, sale.assetId, sale.priceUsd)
                    }

                    return@map createEvent(date, ids)
                }
        }
    }

    private fun createEvent(date: Date, ids: List<Long>): EventModel {
        return EventModel(
            date = date,
            type = eventType,
            entities = ids
        )
    }

}

internal class SaleEventExtractor(
    saleIdProvider: _SaleIdProvider
) : ProvisionEventExtractor(EventType.SALE, saleIdProvider)

internal class DownloadEventExtractor(
    saleIdProvider: _SaleIdProvider
) : ProvisionEventExtractor(EventType.FREE_DOWNLOAD, saleIdProvider)