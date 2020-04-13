package com.vmedia.core.navigation

import androidx.fragment.app.Fragment
import com.vmedia.core.navigation.navigator.sync.SyncScreenMode
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

    object Feed : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_FEED))
    }

    class EventDetails(private val eventId: Long) : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_EVENTDETAILS)) {
            parametersOf(eventId)
        }
    }

}