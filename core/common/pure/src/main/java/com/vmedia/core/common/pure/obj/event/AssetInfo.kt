package com.vmedia.core.common.pure.obj.event

import com.vmedia.core.common.pure.obj.AssetStatus

data class AssetInfo(
    val id: Long,
    val icon: String?,
    val name: String,
    val version: String,
    val status: AssetStatus
)