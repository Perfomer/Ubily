package com.vmedia.ubily

import com.vmedia.core.data.dataModule
import com.vmedia.feature.auth.authModule

val koinModules = listOf(
    dataModule,
    authModule
)