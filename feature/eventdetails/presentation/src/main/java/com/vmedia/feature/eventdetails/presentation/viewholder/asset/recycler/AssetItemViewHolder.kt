package com.vmedia.feature.eventdetails.presentation.viewholder.asset.recycler

import android.view.View
import com.google.android.material.card.MaterialCardView
import com.vmedia.core.common.obj.event.AssetInfo
import com.vmedia.core.common.obj.labelResource
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.loadImageWithRoundedCorners
import com.vmedia.core.common.view.recycler.base.BaseViewHolder
import com.vmedia.feature.eventdetails.presentation.R
import kotlinx.android.synthetic.main.eventdetails_item_asset_item.*

class AssetItemViewHolder(
    containerView: View,
    onClick: (position: Int) -> Unit
) : BaseViewHolder(containerView) {

    init {
        containerView.setOnClickListener { onClick.invokeWithPosition() }
    }

    fun bind(item: AssetInfo) {
        val backgroundColor = resources.getColor(
            if (adapterPosition % 2 == 0) R.color.brand_grey_lightest
            else android.R.color.white
        )

        val card = containerView as MaterialCardView
        card.setCardBackgroundColor(backgroundColor)

        eventdetails_item_asset_title.diffedValue = item.name
        eventdetails_item_asset_version.diffedValue = item.version
        eventdetails_item_asset_status.diffedValue = getString(item.status.labelResource)
        eventdetails_item_asset_icon.loadImageWithRoundedCorners(item.icon)
    }

}