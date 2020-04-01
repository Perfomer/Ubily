package com.vmedia.ubily

import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync.SynchronizationDataType.*
import com.vmedia.core.sync.SynchronizationDataTypeProvider
import io.reactivex.Single

internal class SynchronizationDataTypeProviderImpl : SynchronizationDataTypeProvider{

    override fun shouldSynchronize(type: SynchronizationDataType): Single<Boolean> {
        return Single.fromCallable {
            when(type) {
                PUBLISHER, PERIODS -> true
                ASSETS -> false
                USERS -> false
                REVIEWS -> false
                SALES -> false
                FREE_DOWNLOADS -> false
                REVENUES -> true
                PAYOUTS -> true
            }
        }
    }

}