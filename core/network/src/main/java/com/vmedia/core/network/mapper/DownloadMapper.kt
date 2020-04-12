package com.vmedia.core.network.mapper

import com.vmedia.core.common.util.FORMAT_TABLEVALUES
import com.vmedia.core.common.util.parse
import com.vmedia.core.network.api.entity.ExtraTableValues
import com.vmedia.core.network.entity.DownloadDto

internal object DownloadMapper : TableValuesMapper<DownloadDto>() {

    override fun mapItem(
        dataRow: List<String>,
        extraRow: ExtraTableValues?
    ): DownloadDto {
        return DownloadDto(
            assetName = dataRow[0],
            assetUrl = extraRow!!.shortUrl,
            downloadsQuantity = dataRow[1].toInt(),
            firstDownload = dataRow[2].parse(FORMAT_TABLEVALUES),
            lastDownload = dataRow[3].parse(FORMAT_TABLEVALUES)
        )
    }

}