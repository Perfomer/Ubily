package com.vmedia.core.data.repository.asset.mapper

import com.vmedia.core.common.android.util.ObservableMapper
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.domain.model.AssetShortInfo
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables

internal class AssetShortInfoMapper(
    private val databaseDataSource: DatabaseDataSource
) : ObservableMapper<Asset, AssetShortInfo> {

    override fun map(from: Asset): Observable<AssetShortInfo> {
        return Observables.zip(
            databaseDataSource.getCategory(from.categoryId).toObservable(),
            databaseDataSource.getReviewsCount(from.id)
        ) { category, reviewsCount ->
            AssetShortInfo(
                id = from.id,
                name = from.name,
                categoryName = category.name,
                largeImage = from.bigImage ?: "",
                iconImage = from.iconImage ?: "",
                reviewsCount = reviewsCount,
                averageRating = from.averageRating
            )
        }
    }

}