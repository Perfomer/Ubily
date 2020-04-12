package com.vmedia.core.navigation

import androidx.fragment.app.FragmentActivity
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

const val BEAN_FRAGMENT_AUTH = "AuthFragment"
const val BEAN_FRAGMENT_FEED = "FeedFragment"
const val BEAN_FRAGMENT_SPLASH = "SplashFragment"
const val BEAN_FRAGMENT_SYNC = "SyncFragment"

typealias CiceroneRouter = Cicerone<Router>

val navigationModule = module {
    factory { (activity: FragmentActivity, containerId: Int) ->
        SupportAppNavigator(activity, containerId)
    }

    single { Cicerone.create() }

    single { get<CiceroneRouter>().router }
    single { get<CiceroneRouter>().navigatorHolder }
}