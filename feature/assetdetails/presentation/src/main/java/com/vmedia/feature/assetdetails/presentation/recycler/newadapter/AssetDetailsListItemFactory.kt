package com.vmedia.feature.assetdetails.presentation.recycler.newadapter

import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.core.common.pure.util.flatListOf
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.feature.assetdetails.domain.model.DetailedAsset
import com.vmedia.feature.assetdetails.presentation.recycler.newadapter.listitem.ArtworksListItem
import com.vmedia.feature.assetdetails.presentation.recycler.newadapter.listitem.DescriptionListItem

internal object AssetDetailsListItemFactory {

    fun create(
        asset: DetailedAsset,
        isDescriptionExpanded: Boolean,
    ): List<BaseListItem> {
        return flatListOf(
            createArtworksItem(asset.artworks),
            createDescriptionItem(asset, isDescriptionExpanded)
        )
    }

    private fun createArtworksItem(artworks: List<Artwork>): List<BaseListItem> {
        return if (artworks.isEmpty()) emptyList() else listOf(ArtworksListItem(artworks))
    }

    private fun createDescriptionItem(
        asset: DetailedAsset,
        isDescriptionExpanded: Boolean,
    ): List<BaseListItem> {
        return listOf(DescriptionListItem(asset, isDescriptionExpanded))
    }
}
