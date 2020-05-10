package com.vmedia.feature.publisherdetails.api

import com.vmedia.feature.publisherdetails.domain.di.domainModule
import com.vmedia.feature.publisherdetails.presentation.di.presentationModule

val featurePublisherDetailsModules = domainModule + presentationModule