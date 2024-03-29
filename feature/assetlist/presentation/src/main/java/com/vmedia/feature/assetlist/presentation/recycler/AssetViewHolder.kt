package com.vmedia.feature.assetlist.presentation.recycler

import android.view.View
import com.vmedia.core.common.android.util.diffedValue
import com.vmedia.core.common.android.util.loadCircleImage
import com.vmedia.core.common.android.util.loadImage
import com.vmedia.core.common.android.view.recycler.base.BaseViewHolder
import com.vmedia.core.domain.model.AssetShortInfo
import kotlinx.android.synthetic.main.assetlist_item.*

internal class AssetViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit
) : BaseViewHolder(containerView) {

    init {
        containerView.setOnClickListener { onClick.invokeWithPosition() }
    }

    fun bind(asset: AssetShortInfo) {
        assetlist_item_largeimage.loadImage(asset.largeImage)

        assetlist_item_iconimage.loadCircleImage(asset.iconImage)
        assetlist_item_category.diffedValue = asset.categoryName
        assetlist_item_name.diffedValue = asset.name

        assetlist_item_reviews.diffedValue = asset.reviewsCount.toString()
        assetlist_item_rating.diffedValue = asset.averageRating.toString()
    }

}