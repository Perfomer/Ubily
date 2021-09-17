package com.vmedia.core.sync.synchronizer.review

import com.vmedia.core.common.pure.util.rx.actOnSuccess
import com.vmedia.core.common.pure.util.filterWith
import com.vmedia.core.common.pure.util.mapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Review
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync._ReviewFilter
import com.vmedia.core.sync._ReviewMapper
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single

class ReviewSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val mapper: _ReviewMapper,
    private val filter: _ReviewFilter
) : Synchronizer<List<Review>> {

    override val dataType = SynchronizationDataType.REVIEWS

    override fun execute(): Single<List<Review>> {
        return networkDataSource.getReviews()
            .mapWith(mapper)
            .filterWith(filter)
            .actOnSuccess(databaseDataSource::putReviews)
    }

}
