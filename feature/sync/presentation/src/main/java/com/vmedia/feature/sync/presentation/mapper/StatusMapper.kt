package com.vmedia.feature.sync.presentation.mapper

import com.vmedia.core.common.pure.util.Mapper
import com.vmedia.core.sync.SynchronizationDataType.*
import com.vmedia.core.sync.SynchronizationEvent
import com.vmedia.core.sync.SynchronizationStatus
import com.vmedia.feature.sync.presentation.model.SyncDataItem
import com.vmedia.feature.sync.presentation.model.SyncDataStatus
import com.vmedia.feature.sync.presentation.model.SyncDataStatus.*
import com.vmedia.feature.sync.presentation.model.SyncDataType
import com.vmedia.feature.sync.presentation.model.SyncStatus

internal object StatusMapper : Mapper<SynchronizationStatus, SyncStatus> {

    override fun map(from: SynchronizationStatus): SyncStatus {
        val statuses = emptyStatuses()

        from.events.entries.forEach { (key, value) ->
            val internalKey = when (key) {
                PUBLISHER -> SyncDataType.PUBLISHER
                ASSETS -> SyncDataType.ASSETS
                USERS -> SyncDataType.REVIEWS
                SALES -> SyncDataType.SALES
                FREE_DOWNLOADS -> SyncDataType.DOWNLOADS
                REVENUES -> SyncDataType.REVENUE
                else -> null
            }

            if (internalKey != null) {
                statuses[internalKey] = when (value) {
                    is SynchronizationEvent.Data<*> -> SUCCEED
                    SynchronizationEvent.Loading -> LOADING
                    SynchronizationEvent.Cancelled -> CANCELED
                    is SynchronizationEvent.Error -> FAILED
                }
            }
        }

        return SyncStatus(
            isFinished = from.isFinished,
            hasErrors = from.hasErrors,
            isAuthFailed = from.isAuthFailed,
            dataStatuses = statuses.map { (key, value) ->
                SyncDataItem(
                    type = key,
                    status = value
                )
            }
        )
    }

    private fun emptyStatuses(): MutableMap<SyncDataType, SyncDataStatus> {
        return SyncDataType.values()
            .associate { it to AWAITS }
            .toMutableMap()
    }

}