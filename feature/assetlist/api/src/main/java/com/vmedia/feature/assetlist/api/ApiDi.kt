package com.vmedia.feature.assetlist.api

import com.vmedia.feature.assetlist.domain.di.domainModule
import com.vmedia.feature.assetlist.presentation.di.presentationModule

val featureAssetListModules = domainModule + presentationModule