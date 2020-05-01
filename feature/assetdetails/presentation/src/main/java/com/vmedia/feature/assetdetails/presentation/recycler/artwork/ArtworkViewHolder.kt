package com.vmedia.feature.assetdetails.presentation.recycler.artwork

import android.view.View
import androidx.core.view.isVisible
import com.vmedia.core.common.util.loadImage
import com.vmedia.core.common.view.recycler.base.BaseViewHolder
import com.vmedia.core.common.view.recycler.base.ViewHolderOnClick
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.core.data.internal.database.entity.MediaType.VIDEO
import kotlinx.android.synthetic.main.assetdetails_item_artwork.*

internal class ArtworkViewHolder(
    containerView: View,
    onClick: ViewHolderOnClick
) : BaseViewHolder(containerView) {

    init {
        containerView.setOnClickListener { onClick.invokeWithPosition() }
    }

    fun bind(artwork: Artwork) {
        assetdetails_artwork_item_image.loadImage(artwork.previewUrl)
        assetdetails_artwork_item_play.isVisible = artwork.mediaType == VIDEO
    }

}