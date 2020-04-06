package com.vmedia.core.sync.event.producer

import com.vmedia.core.common.obj.EventType
import com.vmedia.core.data.internal.database.entity.Review
import com.vmedia.core.sync._ReviewIdProvider
import com.vmedia.core.sync.event.EventExtractor
import com.vmedia.core.sync.event.EventModel
import io.reactivex.Single
import java.util.*

internal class ReviewEventExtractor(
    private val reviewIdProvider: _ReviewIdProvider
) : EventExtractor<List<Review>> {

    override fun extract(source: List<Review>): Single<List<EventModel>> {
        return Single.fromCallable {
            source.map { reviewIdProvider.invoke(it.authorId, it.assetId) }
                .map { id ->
                    EventModel(
                        date = Date(System.currentTimeMillis()),
                        type = EventType.REVIEW,
                        entities = listOf(id)
                    )
                }
        }
    }

}