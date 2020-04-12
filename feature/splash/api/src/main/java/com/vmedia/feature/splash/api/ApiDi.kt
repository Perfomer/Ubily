package com.vmedia.feature.splash.api

import com.vmedia.feature.splash.data.di.dataModule
import com.vmedia.feature.splash.domain.di.domainModule
import com.vmedia.feature.splash.presentation.di.presentationModule

val featureSplashModules = dataModule + domainModule + presentationModule