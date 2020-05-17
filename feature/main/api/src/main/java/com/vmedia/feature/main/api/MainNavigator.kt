package com.vmedia.feature.main.api

const val BEAN_FRAGMENT_MAIN = "MainFragment"

interface MainNavigator {

    fun navigateToFeed()

    fun navigateToStatistics()

    fun navigateToPublisher()

    fun navigateToAssetList()

    fun navigateToMenu()

}