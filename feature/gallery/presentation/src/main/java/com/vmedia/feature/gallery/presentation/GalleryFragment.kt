package com.vmedia.feature.gallery.presentation

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.FloatRange
import androidx.core.view.isVisible
import coil.api.load
import com.vmedia.core.common.android.util.addSystemBottomPadding
import com.vmedia.core.common.android.util.argument
import com.vmedia.core.common.android.util.setOnClickListener
import com.vmedia.core.common.android.view.BaseFragment
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.feature.gallery.presentation.recycler.GalleryAdapter
import com.vmedia.feature.gallery.presentation.util.createFlickGestureListener
import com.vmedia.feature.gallery.presentation.util.uihelper.SystemUiHelper
import kotlinx.android.synthetic.main.gallery_fragment.*
import kotlin.math.abs

internal class GalleryFragment : BaseFragment(R.layout.gallery_fragment) {

    private val adapter by lazy { GalleryAdapter(::onArtworkClick) }

    private var artworks: List<Artwork> by argument()
    private var targetArtworkPosition: Int by argument()

    private lateinit var backgroundDrawable: Drawable
    private lateinit var systemUiHelper: SystemUiHelper

    private var uiShown = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundDrawable = view.background.mutate()
        systemUiHelper = SystemUiHelper(activity, SystemUiHelper.LEVEL_IMMERSIVE, 0, null)

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

    override fun onResume() {
        super.onResume()
        setNavigationBarDark(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        systemUiHelper.show()
        setNavigationBarDark(false)
        gallery_images_list.adapter = null
    }

    private fun switchUiVisibility() {
        uiShown = !uiShown
        systemUiHelper.toggle()
        gallery_images_list.isVisible = uiShown
    }

    private fun onArtworkClick(artwork: Artwork) {
        gallery_image.load(artwork.previewUrl)
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