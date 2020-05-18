package com.vmedia.ubily.data

import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync.SynchronizationDataType.*
import com.vmedia.core.sync.datasource.SynchronizationDataTypeProvider
import io.reactivex.Single

internal class SynchronizationDataTypeProviderImpl : SynchronizationDataTypeProvider{

    override fun shouldSynchronize(type: SynchronizationDataType): Single<Boolean> {
        return Single.fromCallable {
            when(type) {
                PUBLISHER, PERIODS, ASSETS, ASSETS_CATEGORIES -> true
                USERS, REVIEWS -> true
                REVENUES, PAYOUTS -> true
                SALES -> true
                FREE_DOWNLOADS -> true
            }
        }
    }

}