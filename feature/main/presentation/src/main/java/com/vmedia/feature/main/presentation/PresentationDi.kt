package com.vmedia.feature.main.presentation

import androidx.fragment.app.Fragment
import com.vmedia.feature.main.api.BEAN_FRAGMENT_MAIN
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureMainPresentationModule = module {
    factory<Fragment>(named(BEAN_FRAGMENT_MAIN)) { MainFragment() }
}