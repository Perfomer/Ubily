package com.vmedia.feature.gallery.presentation.di

import androidx.fragment.app.Fragment
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.core.navigation.BEAN_FRAGMENT_GALLERY
import com.vmedia.feature.gallery.presentation.GalleryFragment
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationModule = module {
    factory<Fragment>(named(BEAN_FRAGMENT_GALLERY)) { (artworks: List<Artwork>, targetArtworkPosition: Int) ->
        GalleryFragment.newInstance(artworks, targetArtworkPosition)
    }
}