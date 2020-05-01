package com.vmedia.feature.assetdetails.presentation.recycler.artwork

import android.view.View
import com.vmedia.core.common.util.loadImage
import com.vmedia.core.common.view.recycler.base.BaseViewHolder
import com.vmedia.core.common.view.recycler.base.ViewHolderOnClick
import kotlinx.android.synthetic.main.assetdetails_item_artwork.*

internal class ArtworkViewHolder(
    containerView: View,
    onClick: ViewHolderOnClick
) : BaseViewHolder(containerView) {

    init {
        containerView.setOnClickListener { onClick.invokeWithPosition() }
    }

    fun bind(artwork: String) {
        assetdetails_artwork_item_image.loadImage(artwork)
    }

}