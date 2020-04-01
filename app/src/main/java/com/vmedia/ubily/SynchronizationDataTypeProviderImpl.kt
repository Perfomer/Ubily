package com.vmedia.ubily

import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync.SynchronizationDataType.*
import com.vmedia.core.sync.datasource.SynchronizationDataTypeProvider
import io.reactivex.Single

internal class SynchronizationDataTypeProviderImpl : SynchronizationDataTypeProvider{

    override fun shouldSynchronize(type: SynchronizationDataType): Single<Boolean> {
        return Single.fromCallable {
            when(type) {
                PUBLISHER, PERIODS -> true
                ASSETS -> true
                USERS -> false
                REVIEWS -> false
                SALES -> true
                FREE_DOWNLOADS -> true
                REVENUES -> false
                PAYOUTS -> false
            }
        }
    }

}