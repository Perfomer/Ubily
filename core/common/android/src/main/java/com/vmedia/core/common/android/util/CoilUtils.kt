package com.vmedia.core.common.android.util

import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import coil.api.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.vmedia.core.common.android.R

fun ImageView.loadImage(
    imageUrl: String?,
    crossfade: Boolean = true,
    @DrawableRes placeholder: Int = R.drawable.bg_placeholder_rect
) {
    load(imageUrl) {
        error(placeholder)
        placeholder(placeholder)
        crossfade(crossfade)
    }
}

fun ImageView.loadRoundedImage(
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

fun ImageView.loadCircleImage(
    imageUrl: String?,
    crossfade: Boolean = true,
    @DrawableRes placeholder: Int = R.drawable.bg_placeholder_circle
) {
    load(imageUrl) {
        error(placeholder)
        placeholder(placeholder)
        crossfade(crossfade)
        transformations(CircleCropTransformation())
    }
}