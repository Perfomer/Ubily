package com.vmedia.feature.sync.presentation.recycler

import android.view.View
import androidx.core.view.isVisible
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.getDrawableCompat
import com.vmedia.core.common.util.getFontCompat
import com.vmedia.core.common.util.setTextColorCompat
import com.vmedia.core.common.view.recycler.base.BaseViewHolder
import com.vmedia.feature.sync.presentation.R
import com.vmedia.feature.sync.presentation.model.SyncDataItem
import com.vmedia.feature.sync.presentation.model.SyncDataStatus.*
import kotlinx.android.synthetic.main.sync_item.*

internal class SyncViewHolder(
    containerView: View
) : BaseViewHolder(containerView) {

    fun bind(item: SyncDataItem) {
        val type = item.type
        val status = item.status

        val icon = when (status) {
            SUCCEED -> R.drawable.ic_check
            FAILED -> R.drawable.ic_error
            else -> null
        }

        val background = when (status) {
            CANCELED -> R.drawable.bg_syncdata_transparent
            SUCCEED -> R.drawable.bg_syncdata_green
            else -> R.drawable.bg_syncdata_grey
        }

        val textColor = when (status) {
            SUCCEED -> R.color.brand_grey_lightest
            FAILED -> R.color.brand_red
            else -> R.color.brand_black
        }

        val alpha = when (status) {
            CANCELED -> 0.5f
            else -> 1f
        }

        val typeface = when (status) {
            SUCCEED, LOADING -> R.font.rubik_medium
            else -> R.font.rubik
        }

        itemView.background = context.getDrawableCompat(background)
        itemView.alpha = alpha

        sync_item_loading.isVisible = status == LOADING
        sync_item_icon.isVisible = icon != null
        sync_item_label.diffedValue = getString(type.labelResource)
        sync_item_label.typeface = context.getFontCompat(typeface)
        sync_item_label.setTextColorCompat(textColor)

        icon?.let(sync_item_icon::setImageResource)
    }

}