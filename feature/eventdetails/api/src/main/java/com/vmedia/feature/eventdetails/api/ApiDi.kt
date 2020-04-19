package com.vmedia.feature.eventdetails.api

import com.vmedia.feature.eventdetails.domain.di.domainModule
import com.vmedia.feature.eventdetails.presentation.di.presentationModule

val featureEventDetailsModules = domainModule + presentationModule