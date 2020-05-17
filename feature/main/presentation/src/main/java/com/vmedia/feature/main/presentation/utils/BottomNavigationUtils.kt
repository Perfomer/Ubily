package com.vmedia.feature.main.presentation.utils

import androidx.fragment.app.Fragment
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.vmedia.core.common.android.R
import com.vmedia.core.common.android.util.addSystemBottomPadding
import com.vmedia.core.common.android.util.getColorCompat
import com.vmedia.feature.main.api.MainNavigator
import com.vmedia.feature.main.api.MainNavigatorScreen

internal val Fragment.mainNavigator
    get() = activity as MainNavigator

internal fun AHBottomNavigation.initMainMenu(
    navigator: MainNavigator,
    currentScreen: MainNavigatorScreen
) {
    addSystemBottomPadding()

    accentColor = context.getColorCompat(android.R.color.white)
    inactiveColor = context.getColorCompat(R.color.white_65)
    defaultBackgroundColor = context.getColorCompat(R.color.brand_green)
    titleState = AHBottomNavigation.TitleState.ALWAYS_HIDE

    setMainNavigator(navigator, currentScreen)
    setCurrentItem(currentScreen.ordinal, false)

    // todo remove
    setNotification("1", 0)
}

private fun AHBottomNavigation.setMainNavigator(
    navigator: MainNavigator,
    currentScreen: MainNavigatorScreen
) {
    val values = MainNavigatorScreen.values()

    removeAllItems()
    addItems(values.map(MainNavigatorScreen::toMenuItem))

    setOnTabSelectedListener { position, wasSelected ->
        if (wasSelected) return@setOnTabSelectedListener false

        values[position].navigatorAction.invoke(navigator)
        setCurrentItem(currentScreen.ordinal, false)
        return@setOnTabSelectedListener true
    }
}

private fun MainNavigatorScreen.toMenuItem(): AHBottomNavigationItem {
    return AHBottomNavigationItem(titleRes, iconRes, R.color.brand_green)
}