package com.vmedia.core.common.util

import android.widget.ImageView
import androidx.annotation.DimenRes
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.vmedia.core.common.R

fun ImageView.loadImageWithRoundedCorners(
    imageUrl: String?,
    crossfade: Boolean = true,
    @DimenRes cornerRadius: Int = R.dimen.asset_icon_corners_radius
) {
    val cornerRadiusValue = resources.getDimension(cornerRadius)

    load(imageUrl) {
        placeholder(R.drawable.bg_placeholder)
        crossfade(crossfade)
        transformations(RoundedCornersTransformation(cornerRadiusValue))
    }
}