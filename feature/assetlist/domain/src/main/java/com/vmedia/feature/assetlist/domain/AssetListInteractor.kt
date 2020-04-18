package com.vmedia.feature.assetlist.domain

import com.vmedia.core.common.util.mapItems
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Publisher
import com.vmedia.feature.assetlist.domain.model.AssetShortInfo
import io.reactivex.Observable

class AssetListInteractor(
    private val source: DatabaseDataSource
) {

    fun getPublisherAvatar(): Observable<String> {
        return source.getPublisher()
            .map(Publisher::smallImageUrl)
            .toObservable()
    }

    fun getAssets(): Observable<List<AssetShortInfo>> {
        return source.getAssets()
            .mapItems {
                AssetShortInfo(
                    id = it.id,
                    categoryId = it.categoryId,
                    name = it.name,
                    largeImage = it.bigImage ?: "",
                    iconImage = it.iconImage ?: "",
                    reviewsCount = 20,
                    averageRating = 5
                )
            }
    }

}