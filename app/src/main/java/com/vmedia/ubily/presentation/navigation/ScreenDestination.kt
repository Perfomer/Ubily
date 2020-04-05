package com.vmedia.ubily.presentation.navigation

import androidx.fragment.app.Fragment
import com.example.feature.feed.BEAN_FRAGMENT_FEED
import com.vmedia.feature.auth.BEAN_FRAGMENT_AUTH
import com.vmedia.feature.splash.BEAN_FRAGMENT_SPLASH
import com.vmedia.feature.sync.BEAN_FRAGMENT_SYNC
import com.vmedia.feature.sync.presentation.SyncScreenMode
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
        override fun getFragment() = get<Fragment>(named(TODO()))
    }

    class Sync(private val mode: SyncScreenMode) : ScreenDestination() {
        override fun getFragment() = get<Fragment>(named(BEAN_FRAGMENT_SYNC)) {
            parametersOf(mode)
        }
    }

    object Feed : ScreenDestination() {
        override fun getFragment() = get<Fragment>((named(BEAN_FRAGMENT_FEED)))
    }

}