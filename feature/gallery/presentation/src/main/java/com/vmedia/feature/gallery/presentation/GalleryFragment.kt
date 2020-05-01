package com.vmedia.feature.gallery.presentation

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.FloatRange
import androidx.core.view.isVisible
import com.vmedia.core.common.util.addSystemBottomPadding
import com.vmedia.core.common.util.argument
import com.vmedia.core.common.util.loadImage
import com.vmedia.core.common.util.setOnClickListener
import com.vmedia.core.common.view.BaseFragment
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.feature.gallery.presentation.recycler.GalleryAdapter
import com.vmedia.feature.gallery.presentation.util.createFlickGestureListener
import kotlinx.android.synthetic.main.gallery_fragment.*
import kotlin.math.abs

internal class GalleryFragment : BaseFragment(R.layout.gallery_fragment) {

    private val adapter by lazy { GalleryAdapter(::onArtworkClick) }

    private var artworks: List<Artwork> by argument()
    private var targetArtworkPosition: Int by argument()

    private lateinit var backgroundDrawable: Drawable

    private var uiShown = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundDrawable = view.background.mutate()
        gallery_images_list.addSystemBottomPadding()

        view.setOnClickListener(::switchUiVisibility)
        gallery_image.setOnClickListener(::switchUiVisibility)

        gallery_images_list.adapter = adapter
        adapter.items = artworks

        gallery_images_list.scrollToPosition(targetArtworkPosition)
        onArtworkClick(artworks[targetArtworkPosition])

        gallery_image_wrap.gestureListener = gallery_image.createFlickGestureListener(
            onMove = { updateBackgroundDimmingAlpha(abs(it)) },
            onDismiss = { view.postDelayed(::goBack, it) }
        )
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

    private fun updateBackgroundDimmingAlpha(
        @FloatRange(from = 0.0, to = 1.0) transparencyFactor: Float
    ) {
        val dimming = 1f - 1f.coerceAtMost(transparencyFactor * 2)

        backgroundDrawable.alpha = (dimming * 255).toInt()
        gallery_images_list?.alpha = dimming
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