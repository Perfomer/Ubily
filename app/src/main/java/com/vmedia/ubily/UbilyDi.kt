package com.vmedia.ubily

import com.vmedia.core.data.dataModules
import com.vmedia.feature.auth.authModule

val koinModules by lazy {
    featureModules + dataModules
}

val featureModules = listOf(
    authModule
)