package com.vmedia.feature.gallery.presentation.di

import androidx.fragment.app.Fragment
import com.vmedia.feature.gallery.api.BEAN_FRAGMENT_GALLERY
import com.vmedia.feature.gallery.presentation.GalleryFragment
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureGalleryPresentationModule = module {
    factory<Fragment>(named(BEAN_FRAGMENT_GALLERY)) { (images: List<String>, targetImagePosition: Int) ->
        GalleryFragment.newInstance(images, targetImagePosition)
    }
}