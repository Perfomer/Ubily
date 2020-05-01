package com.vmedia.feature.gallery.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.vmedia.core.common.util.addSystemBottomPadding
import com.vmedia.core.common.util.argument
import com.vmedia.core.common.util.loadImage
import com.vmedia.core.common.util.setOnClickListener
import com.vmedia.core.common.view.BaseFragment
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.feature.gallery.presentation.recycler.GalleryAdapter
import kotlinx.android.synthetic.main.gallery_fragment.*

internal class GalleryFragment : BaseFragment(R.layout.gallery_fragment) {

    private val adapter by lazy { GalleryAdapter(::onArtworkClick) }

    private var artworks: List<Artwork> by argument()
    private var targetArtworkPosition: Int by argument()

    private var uiShown = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gallery_images_list.addSystemBottomPadding()

        view.setOnClickListener(::switchUiVisibility)
        gallery_image.setOnClickListener(::switchUiVisibility)

        gallery_images_list.adapter = adapter
        adapter.items = artworks

        gallery_images_list.scrollToPosition(targetArtworkPosition)
        onArtworkClick(artworks[targetArtworkPosition])
    }

    override fun onDestroyView() {
        super.onDestroyView()
        gallery_images_list.adapter = null
    }

    private fun switchUiVisibility() {
        uiShown = !uiShown
        gallery_images_list.isVisible = uiShown
    }

    private fun onArtworkClick(artwork: Artwork) {
        gallery_image.loadImage(artwork.previewUrl)
    }

    internal companion object {

        fun newInstance(artworks: List<Artwork>, targetArtworkPosition: Int): GalleryFragment {
            return GalleryFragment().apply {
                this.artworks = artworks
                this.targetArtworkPosition = targetArtworkPosition
            }
        }

    }

}