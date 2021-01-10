package com.vmedia.feature.assetdetails.presentation.recycler.listitem

import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.feature.assetdetails.domain.model.DetailedAsset

internal class AssetListItem(
    val asset: DetailedAsset
) : BaseListItem {
    override val id: String = AssetListItem::class.simpleName!!
}
