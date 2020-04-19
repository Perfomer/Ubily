package com.vmedia.core.common.util

import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.vmedia.core.common.R

fun ImageView.loadImageWithRoundedCorners(
    imageUrl: String?,
    crossfade: Boolean = true,
    @DimenRes cornerRadius: Int = R.dimen.asset_icon_corners_radius,
    @DrawableRes placeholder: Int = R.drawable.bg_placeholder_rect_rounded
) {
    val cornerRadiusValue = resources.getDimension(cornerRadius)

    load(imageUrl) {
        error(placeholder)
        placeholder(placeholder)
        crossfade(crossfade)
        transformations(RoundedCornersTransformation(cornerRadiusValue))
    }
}