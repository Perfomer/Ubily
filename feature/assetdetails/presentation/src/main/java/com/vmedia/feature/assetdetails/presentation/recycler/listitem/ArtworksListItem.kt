package com.vmedia.feature.assetdetails.presentation.recycler.listitem

import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.core.data.internal.database.entity.Artwork

internal class ArtworksListItem(
    val items: List<Artwork>
) : BaseListItem {
    override val id: String = ArtworksListItem::class.simpleName!!
}
