package com.vmedia.feature.main.presentation

import androidx.fragment.app.Fragment
import com.vmedia.core.navigation.BEAN_FRAGMENT_MAIN
import org.koin.core.qualifier.named
import org.koin.dsl.module

val featureMainModules by lazy { listOf(presentationModule) }

val presentationModule = module {
    factory<Fragment>(named(BEAN_FRAGMENT_MAIN)) { MainFragment() }
}