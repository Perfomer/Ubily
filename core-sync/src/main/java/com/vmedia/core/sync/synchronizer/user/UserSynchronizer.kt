package com.vmedia.core.sync.synchronizer.user

import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationEvent.UsersReceived
import com.vmedia.core.sync.SynchronizationEventType
import com.vmedia.core.sync._UserFilter
import com.vmedia.core.sync._UserMapper
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single

class UserSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val mapper: _UserMapper,
    private val filter: _UserFilter
) : Synchronizer<UsersReceived> {

    override val eventType = SynchronizationEventType.USERS_RECEIVED

    override fun execute(): Single<UsersReceived> {
        return networkDataSource.getReviews()
            .filterWith(filter)
            .mapWith(mapper)
            .actOnSuccess(databaseDataSource::putUsers)
            .map(::UsersReceived)
    }

}