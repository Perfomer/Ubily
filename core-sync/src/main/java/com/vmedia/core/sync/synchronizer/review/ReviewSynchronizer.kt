package com.vmedia.core.sync.synchronizer.review

import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationEvent.ReviewsReceived
import com.vmedia.core.sync.SynchronizationEventType
import com.vmedia.core.sync._ReviewFilter
import com.vmedia.core.sync._ReviewMapper
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single

class ReviewSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val mapper: _ReviewMapper,
    private val filter: _ReviewFilter
) : Synchronizer<ReviewsReceived> {

    override val eventType = SynchronizationEventType.REVIEWS_RECEIVED

    override fun execute(): Single<ReviewsReceived> {
        return networkDataSource.getReviews()
            .mapWith(mapper)
            .filterWith(filter)
            .actOnSuccess(databaseDataSource::putReviews)
            .map(::ReviewsReceived)
    }

}