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

    private val Review.updateDate: Date
        get() {
            val replyDate = publisherReply?.publishingDate
            val commentDate = comment.publishingDate

            return if (replyDate == null) commentDate
            else maxOf(comment.publishingDate, replyDate)
        }

    override fun extract(source: List<Review>): Single<List<EventModel>> {
        return Single.fromCallable {
            source
                .map {
                    val id = reviewIdProvider.invoke(it.authorId, it.assetId)

                    EventModel(
                        date = it.updateDate,
                        type = EventType.REVIEW,
                        entities = listOf(id)
                    )
                }
        }
    }

}