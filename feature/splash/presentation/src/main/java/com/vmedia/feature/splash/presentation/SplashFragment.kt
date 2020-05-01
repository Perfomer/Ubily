package com.vmedia.feature.splash.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.vmedia.core.common.android.mvi.MviFragment
import com.vmedia.core.common.android.util.createSnackbar
import com.vmedia.core.common.android.util.isVisible
import com.vmedia.core.common.android.util.toSpan
import com.vmedia.core.navigation.navigator.splash.SplashNavigator
import com.vmedia.feature.splash.presentation.mvi.SplashIntent
import com.vmedia.feature.splash.presentation.mvi.SplashState
import com.vmedia.feature.splash.presentation.mvi.SplashSubscription
import com.vmedia.feature.splash.presentation.mvi.SplashSubscription.InitializingSucceed
import kotlinx.android.synthetic.main.splash_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

internal class SplashFragment : MviFragment<SplashIntent, SplashState, SplashSubscription>(
    layoutResource = R.layout.splash_fragment,
    initialIntent = SplashIntent.Initialize
) {

    private val navigator: SplashNavigator
        get() = activity as SplashNavigator

    private var errorSnackbar: Snackbar? = null

    override fun provideViewModel() = getViewModel<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splash_copyright.text = getString(R.string.app_copyright).toSpan()
    }

    override fun onStart() {
        super.onStart()

        errorSnackbar = createSnackbar(
            textResource = R.string.splash_error,
            duration = Snackbar.LENGTH_INDEFINITE,
            actionTextResource = R.string.erroraction_tryagain,
            action = { postIntent(SplashIntent.Initialize) }
        )
    }

    override fun onStop() {
        super.onStop()
        errorSnackbar = null
    }

    override fun render(state: SplashState) {
        splash_loading.isVisible = state.isLoading
        errorSnackbar?.isVisible = state.error != null
    }

    override fun onSubscriptionReceived(
        subscription: SplashSubscription
    ) = when (subscription) {
        is InitializingSucceed -> {
            val result = subscription.result

            navigator.onInitialized(
                isUserAuthorized = result.isUserAuthorized,
                isUserDataSynchronized = result.synchronizationSucceedAtLeastOnce,
                onboardingAlreadyShown = result.onboardingAlreadyShown
            )
        }
    }

}