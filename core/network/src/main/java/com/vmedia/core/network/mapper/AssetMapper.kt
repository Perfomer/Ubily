package com.vmedia.core.network.mapper

import com.vmedia.core.common.pure.obj.Currency
import com.vmedia.core.common.pure.obj.Money
import com.vmedia.core.common.pure.obj.toAssetStatus
import com.vmedia.core.common.pure.util.FORMAT_PACKAGEVERSION
import com.vmedia.core.common.pure.util.Mapper
import com.vmedia.core.common.pure.util.parse
import com.vmedia.core.network.api.entity.asset.PackageModelWithVersions
import com.vmedia.core.network.api.entity.asset.PackageVersionModel
import com.vmedia.core.network.entity.AssetDto
import com.vmedia.core.network.util.bytesToMegabytes
import java.math.BigDecimal

internal object AssetMapper : Mapper<PackageModelWithVersions, AssetDto> {

    override fun map(from: PackageModelWithVersions): AssetDto {
        val currentVersion = from.lastVersion

        return AssetDto(
            id = from.id,
            packageVersionId = currentVersion.packageVersionId,
            categoryId = from.categoryId,
            name = from.name,
            versionName = currentVersion.versionName,
            averageRating = from.averageRating,
            reviewsQuantity = from.countRatings,
            shortUrl = from.shortUrl,
            status = currentVersion.status.toAssetStatus(),
            sizeMb = currentVersion.size?.bytesToMegabytes() ?: 0.0,
            creationDate = currentVersion.created.parse(FORMAT_PACKAGEVERSION),
            modificationDate = currentVersion.modified.parse(FORMAT_PACKAGEVERSION),
            publishingDate = currentVersion.published?.parse(FORMAT_PACKAGEVERSION),
            price = Money(
                currency = Currency.USD,
                value = BigDecimal(currentVersion.price)
            )
        )
    }

    private val PackageModelWithVersions.lastVersion: PackageVersionModel
        get() = versions.maxBy { it.modified.parse(FORMAT_PACKAGEVERSION) }!!

}