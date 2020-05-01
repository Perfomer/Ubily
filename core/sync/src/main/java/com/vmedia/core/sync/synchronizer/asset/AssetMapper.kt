package com.vmedia.core.sync.synchronizer.asset

import com.vmedia.core.common.android.util.EMPTY_DATE
import com.vmedia.core.common.android.util.Mapper
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.MediaType.IMAGE
import com.vmedia.core.data.internal.database.entity.MediaType.VIDEO
import com.vmedia.core.network.entity.AssetDetailsDto
import com.vmedia.core.network.entity.AssetDto

internal object AssetMapper : Mapper<Pair<AssetDto, AssetDetailsDto>, AssetModel> {

    private const val YOUTUBE_START_ANCHOR = "embed/"
    private const val YOUTUBE_END_ANCHOR = "?"

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
            artworks = details.artworksUrls.map { it.toArtwork(asset.id) },
            keywords = details.tags
        )
    }

    private fun String.toArtwork(assetId: Long): Artwork {
        val isYoutube = isYoutube()

        val (previewUrl, contentUrl, mediaType) =
            if (isYoutube) Triple(extractYoutubePreview(), this, VIDEO)
            else Triple(this, null, IMAGE)

        return Artwork(
            assetId = assetId,
            previewUrl = previewUrl,
            contentUrl = contentUrl,
            mediaType = mediaType
        )
    }

    private fun String.isYoutube() = contains("youtube")

    private fun String.extractYoutubePreview(): String {
        val startAnchorIndex = indexOf(YOUTUBE_START_ANCHOR) + YOUTUBE_START_ANCHOR.length
        val endAnchorIndex = indexOf(YOUTUBE_END_ANCHOR)

        val videoId = substring(startAnchorIndex, endAnchorIndex)

        return "https://img.youtube.com/vi/$videoId/default.jpg"
    }

}