package com.vmedia.feature.main.api

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class MainNavigatorScreen(
    @DrawableRes val iconRes: Int,
    @StringRes val titleRes: Int,
    val navigatorAction: MainNavigator.() -> Unit
) {
    FEED(
        iconRes = R.drawable.ic_menu_feed,
        titleRes = R.string.menu_feed_title,
        navigatorAction = MainNavigator::navigateToFeed
    ),

    STATISTICS(
        iconRes = R.drawable.ic_menu_statistics,
        titleRes = R.string.menu_statistics_title,
        navigatorAction = MainNavigator::navigateToStatistics
    ),

    PUBLISHER(
        iconRes = R.drawable.ic_menu_publisher,
        titleRes = R.string.menu_publisher_title,
        navigatorAction = MainNavigator::navigateToPublisher
    ),

    ASSETLIST(
        iconRes = R.drawable.ic_menu_assets,
        titleRes = R.string.menu_assets_title,
        navigatorAction = MainNavigator::navigateToAssetList
    ),

    MENU(
        iconRes = R.drawable.ic_menu_more,
        titleRes = R.string.menu_more_title,
        navigatorAction = MainNavigator::navigateToMenu
    ),
}