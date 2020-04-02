package com.vmedia.core.sync.event.producer

import com.vmedia.core.common.obj.toPeriod
import com.vmedia.core.data.internal.database.entity.EventType
import com.vmedia.core.data.internal.database.entity.Payout
import com.vmedia.core.sync._PeriodIdProvider
import com.vmedia.core.sync.event.EventExtractor
import com.vmedia.core.sync.event.EventModel
import io.reactivex.Single
import java.util.*

internal class PayoutEventExtractor(
    private val periodIdProvider: _PeriodIdProvider
) : EventExtractor<List<Payout>> {

    override fun extract(source: List<Payout>): Single<List<EventModel>> {
        return Single.fromCallable {
            source.map(Payout::date)
                .map(Date::toPeriod)
                .map(periodIdProvider::invoke)
                .map { id ->
                    EventModel(
                        date = Date(System.currentTimeMillis()),
                        type = EventType.PAYOUT,
                        entities = listOf(id)
                    )
                }
        }
    }

}