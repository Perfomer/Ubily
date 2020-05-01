package com.vmedia.core.sync.event.producer

import androidx.annotation.WorkerThread
import com.vmedia.core.common.android.obj.EventType
import com.vmedia.core.common.android.util.maxValue
import com.vmedia.core.sync.event.EventExtractor
import com.vmedia.core.sync.event.EventModel
import com.vmedia.core.sync.synchronizer.asset.AssetModel
import io.reactivex.Single

internal object AssetEventExtractor : EventExtractor<List<AssetModel>> {

    override fun extract(source: List<AssetModel>): Single<List<EventModel>> {
        return Single.fromCallable { extractEvent(source) }
    }

    @WorkerThread
    private fun extractEvent(source: List<AssetModel>): List<EventModel> {
        if (source.isEmpty()) {
            return emptyList()
        }

        return listOf(
            EventModel(
                date = source.maxValue { it.asset.modificationDate },
                type = EventType.ASSET,
                entities = source.map { it.asset.id }
            )
        )
    }

}