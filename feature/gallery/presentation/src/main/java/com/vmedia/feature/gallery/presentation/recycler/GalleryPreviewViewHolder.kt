package com.vmedia.feature.gallery.presentation.recycler

import android.view.View
import com.vmedia.core.common.android.util.loadImage
import com.vmedia.core.common.android.view.recycler.base.BaseViewHolder
import com.vmedia.core.common.android.view.recycler.base.ViewHolderOnClickListener
import kotlinx.android.synthetic.main.gallery_preview_item.*

internal class GalleryPreviewViewHolder(
    containerView: View,
    onClick: ViewHolderOnClickListener
) : BaseViewHolder(containerView) {

    init {
        containerView.setOnClickListener { onClick.invokeWithPosition() }
    }

    fun bind(imageUrl: String) {
        gallery_artwork_item_image.loadImage(imageUrl)
    }

}
