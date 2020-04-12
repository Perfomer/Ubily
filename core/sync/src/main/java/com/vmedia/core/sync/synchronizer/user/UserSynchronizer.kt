package com.vmedia.core.sync.synchronizer.user

import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.User
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync._UserFilter
import com.vmedia.core.sync._UserMapper
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Single

class UserSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val mapper: _UserMapper,
    private val filter: _UserFilter
) : Synchronizer<List<User>> {

    override val dataType = SynchronizationDataType.USERS

    override fun execute(): Single<List<User>> {
        return networkDataSource.getReviews()
            .filterWith(filter)
            .mapWith(mapper)
            .actOnSuccess(databaseDataSource::putUsers)
    }

}