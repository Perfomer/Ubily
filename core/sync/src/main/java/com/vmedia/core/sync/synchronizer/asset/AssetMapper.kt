package com.vmedia.core.sync.synchronizer.asset

import com.vmedia.core.common.util.EMPTY_DATE
import com.vmedia.core.common.util.Mapper
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.network.entity.AssetDetailsDto
import com.vmedia.core.network.entity.AssetDto

internal object AssetMapper : Mapper<Pair<AssetDto, AssetDetailsDto>, AssetModel> {

    override fun map(from: Pair<AssetDto, AssetDetailsDto>): AssetModel {
        val (asset, details) = from

        return AssetModel(
            asset = Asset(
                id = asset.id,
                categoryId = asset.categoryId,
                versionId = asset.packageVersionId,
                versionName = asset.versionName,
                averageRating = asset.averageRating,
                creationDate = asset.creationDate,
                modificationDate = asset.modificationDate,
                publishingDate = asset.publishingDate ?: EMPTY_DATE,
                priceUsd = asset.price.value,
                totalFileSize = asset.sizeMb,
                status = asset.status,
                name = asset.name,
                shortUrl = asset.shortUrl,
                description = details.description,
                bigImage = details.bigImageUrl,
                smallImage = details.smallImageUrl,
                iconImage = details.iconImageUrl
            ),
            artworks = details.artworksUrls.map {
                Artwork(
                    assetId = asset.id,
                    url = it
                )
            },
            keywords = details.tags
        )
    }

}