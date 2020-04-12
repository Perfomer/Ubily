package com.vmedia.feature.feed.api

import com.vmedia.feature.feed.data.di.dataModule
import com.vmedia.feature.feed.domain.di.domainModule
import com.vmedia.feature.feed.presentation.di.presentationModule

val featureFeedModules = dataModule + domainModule + presentationModule