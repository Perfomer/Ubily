package com.vmedia.core.navigation

import androidx.fragment.app.Fragment
import com.vmedia.feature.gallery.api.BEAN_FRAGMENT_GALLERY
import com.vmedia.feature.main.api.BEAN_FRAGMENT_MAIN
import com.vmedia.feature.publisherdetails.api.BEAN_FRAGMENT_PUBLISHERDETAILS
import com.vmedia.feature.splash.api.BEAN_FRAGMENT_SPLASH
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class ScreenDestination : SupportAppScreen(), KoinComponent {

    abstract class ScreenDestinationNew(
        private val name: String,
        vararg args: Any
    ) : ScreenDestination() {

        private val definitionParameters by lazy { parametersOf(*args) }

        override fun getFragment() = get<Fragment>(named(name)) { definitionParameters }
    }

    object Splash : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_SPLASH))
    }

    object Onboarding : ScreenDestination() {
        override fun getFragment() = TODO()
    }

    object Main : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_MAIN))
    }


    object PublisherDetails : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_PUBLISHERDETAILS))
    }

    class Gallery(
        private val images: List<String>,
        private val targetImagesPosition: Int
    ) : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_GALLERY)) {
            parametersOf(images, targetImagesPosition)
        }
    }

}