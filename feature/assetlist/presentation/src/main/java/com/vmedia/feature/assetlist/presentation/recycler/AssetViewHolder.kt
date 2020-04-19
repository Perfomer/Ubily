package com.vmedia.feature.assetlist.presentation.recycler

import android.view.View
import coil.api.load
import coil.transform.CircleCropTransformation
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.view.recycler.base.BaseViewHolder
import com.vmedia.feature.assetlist.domain.model.AssetShortInfo
import com.vmedia.feature.assetlist.presentation.R
import kotlinx.android.synthetic.main.assetlist_item.*

internal class AssetViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit
) : BaseViewHolder(containerView) {

    init {
        containerView.setOnClickListener { onClick.invokeWithPosition() }
    }

    fun bind(asset: AssetShortInfo) {
        assetlist_item_largeimage.load(asset.largeImage) {
            placeholder(R.drawable.bg_placeholder)
            crossfade(true)
        }

        assetlist_item_iconimage.load(asset.iconImage) {
            placeholder(R.drawable.bg_placeholder)
            crossfade(true)
            transformations(CircleCropTransformation())
        }

        assetlist_item_category.diffedValue = asset.categoryId.toString() //todo
        assetlist_item_name.diffedValue = asset.name

        assetlist_item_reviews.diffedValue = asset.reviewsCount.toString()
        assetlist_item_rating.diffedValue = asset.averageRating.toString()
    }

}