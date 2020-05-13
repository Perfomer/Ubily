package com.vmedia.feature.eventdetails.presentation.viewholder.asset.recycler

import android.view.View
import androidx.annotation.CallSuper
import com.google.android.material.card.MaterialCardView
import com.vmedia.core.common.android.view.recycler.base.BaseViewHolder
import com.vmedia.feature.eventdetails.presentation.R

abstract class BaseAssetItemViewHolder<T : Any>(
    containerView: View,
    onClick: (position: Int) -> Unit
) : BaseViewHolder(containerView) {

    init {
        containerView.setOnClickListener { onClick.invokeWithPosition() }
    }

    @CallSuper
    open fun bind(item: T) {
        val backgroundColor = resources.getColor(
            if (adapterPosition % 2 == 0) R.color.brand_grey_lightest
            else android.R.color.white
        )

        val card = containerView as MaterialCardView
        card.setCardBackgroundColor(backgroundColor)
    }

}