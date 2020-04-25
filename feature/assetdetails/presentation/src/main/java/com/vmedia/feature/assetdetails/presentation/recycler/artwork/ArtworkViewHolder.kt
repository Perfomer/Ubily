package com.vmedia.feature.assetdetails.presentation.recycler.artwork

import android.view.View
import com.vmedia.core.common.util.loadRoundedImage
import com.vmedia.core.common.view.recycler.base.BaseViewHolder
import com.vmedia.core.common.view.recycler.base.ViewHolderOnClick
import com.vmedia.feature.assetdetails.presentation.R
import kotlinx.android.synthetic.main.assetdetails_artwork_item.*

internal class ArtworkViewHolder(
    containerView: View,
    onClick: ViewHolderOnClick
) : BaseViewHolder(containerView) {

    init {
        itemView.setOnClickListener { onClick.invokeWithPosition() }
    }

    fun bind(artwork: String) {
        assetdetails_artwork_item_image.loadRoundedImage(
            imageUrl = artwork,
            cornerRadius = R.dimen.assetdetails_artwork_corner_radius
        )
    }

}