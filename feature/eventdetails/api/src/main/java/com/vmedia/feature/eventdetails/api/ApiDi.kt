package com.vmedia.feature.eventdetails.api

import com.vmedia.feature.eventdetails.presentation.di.presentationModule
import com.vmedia.feature.sync.domain.di.domainModule

val featureEventDetailsModules = domainModule + presentationModule