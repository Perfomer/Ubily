package com.vmedia.core.sync.synchronizer.review

import com.vmedia.core.sync.SynchronizationEvent.ReviewsReceived
import com.vmedia.core.sync.SynchronizationEventType
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single

internal class ReviewSynchronizer : Synchronizer<ReviewsReceived> {

    override val eventType = SynchronizationEventType.REVIEWS_RECEIVED

    override fun execute(): Single<ReviewsReceived> {
        TODO("Not yet implemented")
    }

}