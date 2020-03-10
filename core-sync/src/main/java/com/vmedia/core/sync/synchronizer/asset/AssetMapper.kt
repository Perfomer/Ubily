package com.vmedia.core.sync.synchronizer.asset

import com.vmedia.core.common.util.Mapper
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.AssetImage
import com.vmedia.core.network.entity.AssetDetailsDto
import com.vmedia.core.network.entity.AssetDto

internal object AssetMapper : Mapper<Pair<AssetDto, AssetDetailsDto>, AssetModel> {

    override fun map(from: Pair<AssetDto, AssetDetailsDto>): AssetModel {
        val asset = from.first
        val details = from.second

        return AssetModel(
            asset = Asset(
                id = asset.id,
                categoryId = asset.categoryId,
                versionId = asset.packageVersionId,
                creationDate = asset.creationDate,
                modificationDate = asset.modificationDate,
                publishingDate = asset.publishingDate,
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
            images = details.artworksUrls.map { AssetImage(asset.id, it) },
            keywords = details.tags
        )
    }

}