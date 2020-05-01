package com.vmedia.core.sync.event.producer

import com.vmedia.core.common.android.obj.EventType
import com.vmedia.core.data.internal.database.entity.Payout
import com.vmedia.core.sync.event.EventExtractor
import com.vmedia.core.sync.event.EventModel
import io.reactivex.Single

internal object PayoutEventExtractor : EventExtractor<List<Payout>> {

    override fun extract(source: List<Payout>): Single<List<EventModel>> {
        return Single.fromCallable {
            source.map {
                EventModel(
                    date = it.date,
                    type = EventType.PAYOUT,
                    entities = listOf(it.periodId)
                )
            }
        }
    }

}