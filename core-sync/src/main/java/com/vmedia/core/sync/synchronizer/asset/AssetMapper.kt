package com.vmedia.core.sync.synchronizer.asset

import com.vmedia.core.common.util.Mapper
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.AssetImage
import com.vmedia.core.network.entity.AssetDetailsDto
import com.vmedia.core.network.entity.AssetDto
import java.util.*

internal object AssetMapper : Mapper<Pair<AssetDto, AssetDetailsDto>, AssetModel> {

    private val EMPTY_DATE = Date(0L)

    override fun map(from: Pair<AssetDto, AssetDetailsDto>): AssetModel {
        val (asset, details) = from

        return AssetModel(
            asset = Asset(
                id = asset.id,
                categoryId = asset.categoryId,
                versionId = asset.packageVersionId,
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
            images = details.artworksUrls.map {
                AssetImage(
                    assetId = asset.id,
                    url = it
                )
            },
            keywords = details.tags
        )
    }

}