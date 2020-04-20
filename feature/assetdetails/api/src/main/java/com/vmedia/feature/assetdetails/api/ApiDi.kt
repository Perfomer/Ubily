package com.vmedia.feature.assetdetails.api

import com.vmedia.feature.assetdetails.domain.di.domainModule
import com.vmedia.feature.assetdetails.presentation.di.presentationModule


val featureAssetDetailsModules = domainModule + presentationModule