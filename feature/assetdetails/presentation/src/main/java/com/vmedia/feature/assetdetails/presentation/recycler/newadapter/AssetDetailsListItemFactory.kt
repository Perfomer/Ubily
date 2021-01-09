package com.vmedia.feature.assetdetails.presentation.recycler.newadapter

import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.core.common.pure.util.flatListOf
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.feature.assetdetails.presentation.recycler.newadapter.listitem.ArtworksListItem

internal object AssetDetailsListItemFactory {

    fun create(
        artworks: List<Artwork>,
    ): List<BaseListItem> {
        return flatListOf(
            createArtworksItem(artworks),
        )
    }

    private fun createArtworksItem(artworks: List<Artwork>): List<BaseListItem> {
        return if (artworks.isEmpty()) emptyList() else listOf(ArtworksListItem(artworks))
    }

}
