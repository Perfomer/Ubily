package com.vmedia.feature.assetdetails.domain

import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.feature.assetdetails.domain.model.AssetDetails
import io.reactivex.Observable

class AssetDetailsInteractor(
    private val source: DatabaseDataSource
) {

    fun getAsset(id: Long) : Observable<AssetDetails> {
        return source.getAsset(id)
            .map {
                AssetDetails(
                    id = id,
                    categoryName = it.categoryId.toString(),
                    name = it.name,
                    versionName = it.versionName,
                    priceUsd = it.priceUsd,
                    status = it.status,
                    sizeMb = it.totalFileSize,
                    description = it.description,
                    bigImage = it.bigImage?:"",
                    iconImage = it.iconImage?:""
                )
            }
            .toObservable()
    }

}