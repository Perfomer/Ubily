package com.vmedia.feature.assetdetails.presentation.recycler.listitem

import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.feature.assetdetails.domain.model.PublisherModel

internal class PublisherListItem(
    val publisher: PublisherModel,
) : BaseListItem {
    override val id: String = PublisherListItem::class.simpleName!!
}
