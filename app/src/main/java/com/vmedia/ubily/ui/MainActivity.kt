package com.vmedia.ubily.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vmedia.core.common.util.toast
import com.vmedia.feature.auth.presentation.AuthNavigator
import com.vmedia.feature.splash.BEAN_FRAGMENT_SPLASH
import com.vmedia.feature.splash.presentation.SplashNavigator
import com.vmedia.ubily.R
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity(),
    AuthNavigator,
    SplashNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.nav_host_fragment, get(named(BEAN_FRAGMENT_SPLASH)))
            .addToBackStack("asdasda")
            .commit()
    }

    override fun onInitialized(isUserAuthorized: Boolean, isUserDataSynchronized: Boolean) {
        toast("isUserAuthorized: $isUserAuthorized, isUserDataSynchronized: $isUserDataSynchronized")
    }

    override fun onAuthSucceed() {
        toast("Auth Succeed")
    }

}