package com.vmedia.core.network.mapper

import com.vmedia.core.common.util.FORMAT_yyyyMMdd
import com.vmedia.core.common.util.parse
import com.vmedia.core.network.api.entity.DownloadDto

internal object DownloadMapper : TableValuesMapper<DownloadDto>() {

    override fun mapItem(tableValues: List<String>): DownloadDto {
        return DownloadDto(
            assetName = tableValues[0],
            downloadsQuantity = tableValues[1].toInt(),
            firstDownload = tableValues[2].parse(FORMAT_yyyyMMdd),
            lastDownload = tableValues[3].parse(FORMAT_yyyyMMdd)
        )
    }

}