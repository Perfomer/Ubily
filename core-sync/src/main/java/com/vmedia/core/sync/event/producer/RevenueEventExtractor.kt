package com.vmedia.core.sync.event.producer

import com.vmedia.core.common.obj.EventType
import com.vmedia.core.data.internal.database.entity.Revenue
import com.vmedia.core.sync.event.EventExtractor
import com.vmedia.core.sync.event.EventModel
import io.reactivex.Single
import java.util.*

internal object RevenueEventExtractor : EventExtractor<List<Revenue>> {

    override fun extract(source: List<Revenue>): Single<List<EventModel>> {
        return Single.fromCallable {
            source.map {
                EventModel(
                    date = Date(System.currentTimeMillis()),
                    type = EventType.REVENUE,
                    entities = listOf(it.periodId)
                )
            }
        }
    }

}