package com.vmedia.ubily.presentation

import com.vmedia.core.navigation.NavigationActivity
import com.vmedia.core.navigation.ScreenDestination
import com.vmedia.core.navigation.navigator.assetdetails.AssetDetailsNavigator
import com.vmedia.core.navigation.navigator.assetlist.AssetListNavigator
import com.vmedia.core.navigation.navigator.auth.AuthNavigator
import com.vmedia.core.navigation.navigator.eventdetails.EventDetailsNavigator
import com.vmedia.core.navigation.navigator.feed.FeedNavigator
import com.vmedia.core.navigation.navigator.main.MainNavigator
import com.vmedia.core.navigation.navigator.splash.SplashNavigator
import com.vmedia.core.navigation.navigator.sync.SyncNavigator
import com.vmedia.core.navigation.navigator.sync.SyncScreenMode
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
        TODO("Not yet implemented")
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