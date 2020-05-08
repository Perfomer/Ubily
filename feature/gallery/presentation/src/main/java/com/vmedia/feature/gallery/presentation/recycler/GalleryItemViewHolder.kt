package com.vmedia.feature.gallery.presentation.recycler

import android.view.View
import com.vmedia.core.common.android.util.loadImage
import com.vmedia.core.common.android.view.recycler.base.BaseViewHolder
import com.vmedia.core.data.internal.database.entity.Artwork
import kotlinx.android.synthetic.main.gallery_item.*

internal class GalleryItemViewHolder(
    containerView: View
) : BaseViewHolder(containerView) {

    fun bind(artwork: Artwork) {
        gallery_item_image.loadImage(artwork.previewUrl)
    }

}