package com.vmedia.core.navigation

import androidx.fragment.app.FragmentActivity
import com.vmedia.core.navigation.cicerone.UbilyNavigator
import com.vmedia.core.navigation.cicerone.UbilyRouter
import org.koin.dsl.module
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router

const val BEAN_FRAGMENT_MAIN = "MainFragment"
const val BEAN_FRAGMENT_AUTH = "AuthFragment"
const val BEAN_FRAGMENT_FEED = "FeedFragment"
const val BEAN_FRAGMENT_EVENTDETAILS = "EventDetailsFragment"
const val BEAN_FRAGMENT_PUBLISHERDETAILS = "PublisherDetailsFragment"
const val BEAN_FRAGMENT_SPLASH = "SplashFragment"
const val BEAN_FRAGMENT_GALLERY = "GalleryFragment"
const val BEAN_FRAGMENT_SYNC = "SyncFragment"
const val BEAN_FRAGMENT_MENU = "MenuFragment"
const val BEAN_FRAGMENT_STATISTICS = "StatisticsFragment"

val navigationModule = module {
    factory<Navigator> { (activity: FragmentActivity, containerId: Int) ->
        UbilyNavigator(activity, containerId)
    }

    single { Cicerone.create(get()) }
    factory<BaseRouter> { UbilyRouter() }

    single { get<Cicerone<Router>>().router as UbilyRouter }
    single { get<Cicerone<Router>>().navigatorHolder }
}