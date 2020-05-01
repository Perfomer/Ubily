package com.vmedia.core.network.mapper

import com.vmedia.core.common.pure.util.Mapper
import com.vmedia.core.network.api.entity.asset.LanguageMetadataModel
import com.vmedia.core.network.entity.AssetDetailsDto
import com.vmedia.core.network.util.fixUrl

internal object AssetDetailsMapper : Mapper<LanguageMetadataModel, AssetDetailsDto> {

    override fun map(from: LanguageMetadataModel): AssetDetailsDto {
        val keyImages = from.keyImages

        val smallImageUrl = keyImages.smallUpdated ?: keyImages.small
        val bigImageUrl = keyImages.bigUpdated ?: keyImages.big

        return AssetDetailsDto(
            description = from.description,
            iconImageUrl = keyImages.icon?.fixUrl(),
            smallImageUrl = smallImageUrl?.fixUrl(),
            bigImageUrl = bigImageUrl?.fixUrl(),
            facebookImageUrl = keyImages.facebook?.fixUrl(),
            artworksUrls = from.artworks.map { it.uri.fixUrl() }.toSet(),
            tags = from.keywords?.split(", ")?.toSet() ?: emptySet()
        )
    }

}