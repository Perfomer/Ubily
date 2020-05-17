package com.vmedia.feature.gallery.api

import com.vmedia.core.navigation.ScreenDestination.ScreenDestinationNew

class GalleryScreen(
    images: List<String>,
    targetImagesPosition: Int
) : ScreenDestinationNew(
    BEAN_FRAGMENT_GALLERY,
    images,
    targetImagesPosition
)