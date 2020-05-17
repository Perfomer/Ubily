package com.vmedia.ubily.presentation

import com.vmedia.core.navigation.NavigationActivity
import com.vmedia.core.navigation.ScreenDestination
import com.vmedia.core.navigation.navigator.main.MainNavigator
import com.vmedia.core.navigation.navigator.splash.SplashNavigator
import com.vmedia.feature.assetdetails.api.AssetDetailsNavigator
import com.vmedia.feature.assetlist.api.AssetListNavigator
import com.vmedia.feature.auth.api.AuthNavigator
import com.vmedia.feature.eventdetails.api.EventDetailsNavigator
import com.vmedia.feature.feed.api.FeedNavigator
import com.vmedia.feature.publisherdetails.api.PublisherDetailsNavigator
import com.vmedia.feature.sync.api.SyncNavigator
import com.vmedia.feature.sync.api.SyncScreenMode
import com.vmedia.ubily.R

internal class UbilyActivity : NavigationActivity(
    screenLayoutResource = R.layout.ubily_activity,
    frameLayoutResource = R.id.nav_host_fragment,
    startScreen = ScreenDestination.Splash
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
    private var onboardingAlreadyShown: Boolean = false

    override fun navigateToEventDetails(eventId: Long) {
        navigateTo(ScreenDestination.EventDetails(eventId))
    }

    override fun navigateToAsset(assetId: Long) {
        navigateTo(ScreenDestination.AssetDetails(assetId))
    }

    override fun navigateToFeed() {
        navigateTo(ScreenDestination.Feed)
    }

    override fun navigateToAssetList() {
        navigateTo(ScreenDestination.AssetList)
    }

    override fun navigateToMenu() {
        TODO("Not yet implemented")
    }

    override fun navigateToAssetSearch() {
        TODO("Not yet implemented")
    }

    override fun navigateToPublisher() {
        navigateTo(ScreenDestination.PublisherDetails)
    }

    override fun navigateToUser(id: Long) {
        TODO("Not yet implemented")
    }

    override fun navigateToAssetsSearch(keywordId: Long) {
        TODO("Not yet implemented")
    }

    override fun navigateToGallery(images: List<String>, targetImagesPosition: Int) {
        addOver(ScreenDestination.Gallery(images, targetImagesPosition))
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
        onboardingAlreadyShown: Boolean
    ) {
        this.isUserAuthorized = isUserAuthorized
        this.isUserDataSynchronized = isUserDataSynchronized
        this.onboardingAlreadyShown = onboardingAlreadyShown

        navigateThroughStartGraph()
    }

    override fun onSynchronizationSucceed() {
        isUserDataSynchronized = true
        navigateThroughStartGraph()
    }

    override fun onAuthSucceed() {
        isUserAuthorized = true
        navigateThroughStartGraph()
    }

    private fun navigateThroughStartGraph() {
        if (isUserAuthorized) {
            if (isUserDataSynchronized) {
                navigateTo(ScreenDestination.Main)
            } else {
//                if (onboardingAlreadyShown) {
                navigateTo(ScreenDestination.Sync(SyncScreenMode.INITIAL))
//                } else {
//                    navigateTo(ScreenDestination.Onboarding)
//                }
            }
        } else {
            navigateTo(ScreenDestination.Auth)
        }
    }

}