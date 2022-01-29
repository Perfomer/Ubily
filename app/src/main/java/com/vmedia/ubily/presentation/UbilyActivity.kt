package com.vmedia.ubily.presentation

import com.vmedia.core.navigation.NavigationActivity
import com.vmedia.feature.assetdetails.api.AssetDetailsNavigator
import com.vmedia.feature.assetdetails.api.AssetDetailsScreen
import com.vmedia.feature.assetlist.api.AssetListNavigator
import com.vmedia.feature.assetlist.api.AssetListScreen
import com.vmedia.feature.auth.api.AuthNavigator
import com.vmedia.feature.auth.api.AuthScreen
import com.vmedia.feature.eventdetails.api.EventDetailsNavigator
import com.vmedia.feature.eventdetails.api.EventDetailsScreen
import com.vmedia.feature.feed.api.FeedNavigator
import com.vmedia.feature.feed.api.FeedScreen
import com.vmedia.feature.gallery.api.GalleryScreen
import com.vmedia.feature.main.api.MainNavigator
import com.vmedia.feature.main.api.MainScreen
import com.vmedia.feature.publisherdetails.api.PublisherDetailsNavigator
import com.vmedia.feature.publisherdetails.api.PublisherDetailsScreen
import com.vmedia.feature.splash.api.SplashNavigator
import com.vmedia.feature.splash.api.SplashScreen
import com.vmedia.feature.sync.api.SyncNavigator
import com.vmedia.feature.sync.api.SyncScreen
import com.vmedia.feature.sync.api.SyncScreenMode
import com.vmedia.ubily.R

internal class UbilyActivity : NavigationActivity(
    screenLayoutResource = R.layout.ubily_activity,
    frameLayoutResource = R.id.nav_host_fragment,
    startScreen = SplashScreen
), MainNavigator,
    SplashNavigator,
    SyncNavigator,
    AuthNavigator,
    FeedNavigator,
    EventDetailsNavigator,
    PublisherDetailsNavigator,
    AssetListNavigator,
    AssetDetailsNavigator {

    private var isUserAuthorized: Boolean = false
    private var isUserDataSynchronized: Boolean = false
    private var isOnboardingAlreadyShown: Boolean = false

    override fun navigateToEventDetails(eventId: Long) {
        navigateTo(EventDetailsScreen(eventId))
    }

    override fun navigateToAsset(assetId: Long) {
        navigateTo(AssetDetailsScreen(assetId))
    }

    override fun navigateToFeed() {
        navigateTo(FeedScreen)
    }

    override fun navigateToAssetList() {
        navigateTo(AssetListScreen)
    }

    override fun navigateToMenu() {
        TODO("Not yet implemented")
    }

    override fun navigateToAssetSearch() {
        TODO("Not yet implemented")
    }

    override fun navigateToPublisher() {
        navigateTo(PublisherDetailsScreen)
    }

    override fun navigateToUser(id: Long) {
        TODO("Not yet implemented")
    }

    override fun navigateToAssetsSearch(keywordId: Long) {
        TODO("Not yet implemented")
    }

    override fun navigateToGallery(images: List<String>, targetImagesPosition: Int) {
        addOver(GalleryScreen(images, targetImagesPosition))
    }

    override fun navigateToGallery(imageUrl: String) {
        navigateToGallery(listOf(imageUrl))
    }

    override fun navigateToUrl(url: String) {
        TODO("Not yet implemented")
    }

    override fun navigateToStatistics() {
        TODO("Not yet implemented")
    }

    override fun navigateToStatistics(periodId: Long) {
        navigateToAssetList()
    }

    override fun onInitialized(
        isUserAuthorized: Boolean,
        isUserDataSynchronized: Boolean,
        isOnboardingAlreadyShown: Boolean
    ) {
        this.isUserAuthorized = isUserAuthorized
        this.isUserDataSynchronized = isUserDataSynchronized
        this.isOnboardingAlreadyShown = isOnboardingAlreadyShown

        navigateThroughStartGraph()
    }

    override fun onSynchronizationSucceed() {
        isUserDataSynchronized = true
        navigateThroughStartGraph()
    }

    override fun onSynchronizationFailed() {
        isUserAuthorized = false
        navigateThroughStartGraph()
    }

    override fun onAuthSucceed() {
        isUserAuthorized = true
        navigateThroughStartGraph()
    }

    private fun navigateThroughStartGraph() {
        if (isUserAuthorized) {
            if (isUserDataSynchronized) {
                navigateTo(MainScreen)
            } else {
//                if (onboardingAlreadyShown) {
                navigateTo(SyncScreen(SyncScreenMode.INITIAL))
//                } else {
//                    navigateTo(ScreenDestination.Onboarding)
//                }
            }
        } else {
            navigateTo(AuthScreen)
        }
    }

}
