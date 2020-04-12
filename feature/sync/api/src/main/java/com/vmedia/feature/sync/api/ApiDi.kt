package com.vmedia.feature.sync.api

import com.vmedia.feature.sync.domain.di.domainModule
import com.vmedia.feature.sync.presentation.di.presentationModule

val featureSyncModules = domainModule + presentationModule