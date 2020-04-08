package com.example.feature.feed.domain.mapper

import androidx.annotation.WorkerThread
import com.example.feature.feed.domain.FeedRepository
import com.vmedia.core.common.util.ObservableMapper
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.obj.AssetInfo
import com.vmedia.core.data.obj.EventInfo.EventListInfo.EventAsset
import io.reactivex.Observable

internal class AssetMapper(
    private val repository: FeedRepository
) : ObservableMapper<Event, EventAsset> {

    override fun map(from: Event): Observable<EventAsset> {
        return repository.getEventAssets(from.id)
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