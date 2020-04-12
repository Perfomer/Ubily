package com.vmedia.feature.auth.api

import com.vmedia.feature.auth.data.di.dataModule
import com.vmedia.feature.auth.domain.di.domainModule
import com.vmedia.feature.auth.presentation.di.presentationModule

val authModules = dataModule + domainModule + presentationModule