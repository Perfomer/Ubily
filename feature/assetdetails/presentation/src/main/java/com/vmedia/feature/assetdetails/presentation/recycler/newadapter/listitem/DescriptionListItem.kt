package com.vmedia.feature.assetdetails.presentation.recycler.newadapter.listitem

import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.feature.assetdetails.domain.model.DetailedAsset

internal class DescriptionListItem(
    val asset: DetailedAsset,
    val isDescriptionExpanded: Boolean,
) : BaseListItem {
    override val id: String = DescriptionListItem::class.simpleName!!
}
