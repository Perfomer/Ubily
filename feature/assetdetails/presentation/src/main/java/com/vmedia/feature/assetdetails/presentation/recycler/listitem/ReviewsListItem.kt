package com.vmedia.feature.assetdetails.presentation.recycler.listitem

import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.core.common.pure.obj.ReviewsSortType
import com.vmedia.feature.assetdetails.domain.model.ReviewsModel

internal class ReviewsListItem(
    val reviews: ReviewsModel,
    val isReviewsExpanded: Boolean = false,
    val reviewsSortType: ReviewsSortType,
) : BaseListItem {
    override val id: String = ReviewsListItem::class.simpleName!!
}
