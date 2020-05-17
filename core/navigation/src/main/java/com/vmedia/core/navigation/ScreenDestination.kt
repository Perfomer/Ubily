package com.vmedia.core.navigation

import androidx.fragment.app.Fragment
import com.vmedia.feature.assetdetails.api.BEAN_FRAGMENT_ASSETDETAILS
import com.vmedia.feature.assetlist.api.BEAN_FRAGMENT_ASSETLIST
import com.vmedia.feature.auth.api.BEAN_FRAGMENT_AUTH
import com.vmedia.feature.eventdetails.api.BEAN_FRAGMENT_EVENTDETAILS
import com.vmedia.feature.feed.api.BEAN_FRAGMENT_FEED
import com.vmedia.feature.gallery.api.BEAN_FRAGMENT_GALLERY
import com.vmedia.feature.main.api.BEAN_FRAGMENT_MAIN
import com.vmedia.feature.publisherdetails.api.BEAN_FRAGMENT_PUBLISHERDETAILS
import com.vmedia.feature.splash.api.BEAN_FRAGMENT_SPLASH
import com.vmedia.feature.sync.api.BEAN_FRAGMENT_SYNC
import com.vmedia.feature.sync.api.SyncScreenMode
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class ScreenDestination : SupportAppScreen(), KoinComponent {

    object Splash : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_SPLASH))
    }

    object Auth : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_AUTH))
    }

    object Onboarding : ScreenDestination() {
        override fun getFragment() = TODO()
    }

    class Sync(private val mode: SyncScreenMode) : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_SYNC)) {
            parametersOf(mode)
        }
    }

    object Main : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_MAIN))
    }

    object Feed : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_FEED))
    }

    class EventDetails(private val eventId: Long) : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_EVENTDETAILS)) {
            parametersOf(eventId)
        }
    }

    object PublisherDetails : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_PUBLISHERDETAILS))
    }

    class AssetDetails(private val assetId: Long) : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_ASSETDETAILS)) {
            parametersOf(assetId)
        }
    }

    object AssetList : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_ASSETLIST))
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