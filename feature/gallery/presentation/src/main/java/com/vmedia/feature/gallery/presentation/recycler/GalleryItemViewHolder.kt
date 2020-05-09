package com.vmedia.feature.gallery.presentation.recycler

import android.view.View
import com.vmedia.core.common.android.util.loadImage
import com.vmedia.core.common.android.view.recycler.base.BaseViewHolder
import kotlinx.android.synthetic.main.gallery_item.*

internal class GalleryItemViewHolder(
    containerView: View
) : BaseViewHolder(containerView) {

    fun bind(imageUrl: String) {
        gallery_item_image.loadImage(imageUrl)
    }

}