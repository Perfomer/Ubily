package com.vmedia.feature.gallery.api

import com.vmedia.core.navigation.ScreenDestination

class GalleryScreen(
    images: List<String>,
    targetImagesPosition: Int
) : ScreenDestination(
    BEAN_FRAGMENT_GALLERY,
    images,
    targetImagesPosition
)