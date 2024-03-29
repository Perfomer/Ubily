package com.vmedia.core.navigation

import androidx.fragment.app.FragmentActivity
import com.vmedia.core.navigation.cicerone.UbilyNavigator
import com.vmedia.core.navigation.cicerone.UbilyRouter
import org.koin.dsl.module
import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router

val navigationModule = module {
    factory<Navigator> { (activity: FragmentActivity, containerId: Int) ->
        UbilyNavigator(activity, containerId)
    }

    single { Cicerone.create(get()) }
    factory<BaseRouter> { UbilyRouter() }

    single { get<Cicerone<Router>>().router as UbilyRouter }
    single { get<Cicerone<Router>>().navigatorHolder }
}