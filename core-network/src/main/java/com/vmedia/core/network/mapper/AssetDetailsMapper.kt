package com.vmedia.core.network.mapper

import com.vmedia.core.common.util.Mapper
import com.vmedia.core.network.api.entity.rest.AssetDetailsDto
import com.vmedia.core.network.api.entity.rest.asset.LanguageMetadataModel

object AssetDetailsMapper : Mapper<LanguageMetadataModel, AssetDetailsDto> {

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

    private fun String.fixUrl(): String {
        if (isBlank()) return this
        return "http:$this"
    }

}