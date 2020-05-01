package com.vmedia.core.data.repository.event.mapper

import androidx.annotation.WorkerThread
import com.vmedia.core.common.pure.obj.event.AssetInfo
import com.vmedia.core.common.pure.obj.event.EventInfo.EventListInfo.EventAsset
import com.vmedia.core.common.pure.util.ObservableMapper
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Event
import io.reactivex.Observable

internal class AssetMapper(
    private val source: DatabaseDataSource
) : ObservableMapper<Event, EventAsset> {

    override fun map(from: Event): Observable<EventAsset> {
        return source.getEventAssets(from.id)
            .map { createEventAsset(from, it) }
            .toObservable()
    }

    @WorkerThread
    private fun createEventAsset(event: Event, assets: List<Asset>): EventAsset {
        val assetsInfo = assets.map(::createAssetInfo)

        return EventAsset(
            id = event.id,
            date = event.date,
            content = assetsInfo
        )
    }

    @WorkerThread
    private fun createAssetInfo(asset: Asset): AssetInfo {
        return AssetInfo(
            id = asset.id,
            icon = asset.iconImage,
            name = asset.name,
            version = asset.versionName,
            status = asset.status
        )
    }

}