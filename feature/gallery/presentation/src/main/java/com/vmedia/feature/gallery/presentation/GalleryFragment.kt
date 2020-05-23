package com.vmedia.feature.gallery.presentation

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.FloatRange
import androidx.core.view.isVisible
import com.vmedia.core.common.android.util.addSystemBottomPadding
import com.vmedia.core.common.android.util.argument
import com.vmedia.core.common.android.util.setOnClickListener
import com.vmedia.core.common.android.util.setOnPageSelectedListener
import com.vmedia.core.common.android.view.BaseFragment
import com.vmedia.core.common.android.view.system.SystemUiColorMode
import com.vmedia.feature.gallery.presentation.recycler.GalleryAdapter
import com.vmedia.feature.gallery.presentation.recycler.GalleryPreviewAdapter
import com.vmedia.feature.gallery.presentation.util.createFlickGestureListener
import com.vmedia.feature.gallery.presentation.util.uihelper.SystemUiHelper
import kotlinx.android.synthetic.main.gallery_fragment.*
import kotlin.math.abs

internal class GalleryFragment : BaseFragment(R.layout.gallery_fragment) {

    private val previewAdapter by lazy { GalleryPreviewAdapter(gallery_pager::setCurrentItem) }
    private val adapter by lazy { GalleryAdapter() }

    private var images: List<String> by argument()
    private var targetImagePosition: Int by argument()

    private lateinit var backgroundDrawable: Drawable
    private lateinit var systemUiHelper: SystemUiHelper

    private lateinit var prevSystemUiColorMode: SystemUiColorMode

    private var uiShown = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundDrawable = view.background.mutate()
        systemUiHelper = SystemUiHelper(activity, SystemUiHelper.LEVEL_IMMERSIVE, 0, null)

        gallery_images_list.addSystemBottomPadding()

        view.setOnClickListener(::switchUiVisibility)
        gallery_pager.setOnClickListener(::switchUiVisibility)

        initPreviews()
        initPager()

        gallery_image_wrap.gestureListener = gallery_pager.createFlickGestureListener(
            onMove = { updateBackgroundDimmingAlpha(abs(it)) },
            onDismiss = { view.postDelayed(::goBack, it) }
        )
    }

    override fun onResume() {
        super.onResume()
        prevSystemUiColorMode = systemUiColorMode
        systemUiColorMode = SystemUiColorMode.Dark
    }

    override fun onDestroyView() {
        super.onDestroyView()
        systemUiHelper.show()
        prevSystemUiColorMode = prevSystemUiColorMode
        gallery_images_list.adapter = null
    }

    private fun initPager() {
        gallery_pager.setOnPageSelectedListener(gallery_images_list::smoothScrollToPosition)
        gallery_pager.adapter = adapter

        adapter.items = images
        gallery_pager.currentItem = targetImagePosition
    }

    private fun initPreviews() {
        if (images.size < 2) {
            gallery_images_list.isVisible = false
            return
        }

        gallery_images_list.adapter = previewAdapter
        gallery_images_list.scrollToPosition(targetImagePosition)

        previewAdapter.items = images
    }

    private fun switchUiVisibility() {
        uiShown = !uiShown
        systemUiHelper.toggle()
        gallery_images_list.isVisible = uiShown
    }

    private fun updateBackgroundDimmingAlpha(
        @FloatRange(from = 0.0, to = 1.0) transparencyFactor: Float
    ) {
        val dimming = 1f - 1f.coerceAtMost(transparencyFactor * 2)

        backgroundDrawable.alpha = (dimming * 255).toInt()
        gallery_images_list?.alpha = dimming
    }


    internal companion object {

        fun newInstance(images: List<String>, targetImagePosition: Int): GalleryFragment {
            return GalleryFragment().apply {
                this.images = images
                this.targetImagePosition = targetImagePosition
            }
        }

    }

}