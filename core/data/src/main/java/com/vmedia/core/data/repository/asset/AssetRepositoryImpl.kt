package com.vmedia.core.data.repository.asset

import com.vmedia.core.common.obj.AssetStatus
import com.vmedia.core.common.util.flatMapWith
import com.vmedia.core.data._AssetShortInfoMapper
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Publisher
import com.vmedia.core.domain.model.AssetShortInfo
import com.vmedia.core.domain.repository.AssetRepository
import io.reactivex.Observable

internal class AssetRepositoryImpl(
    private val source: AssetCacheDatabaseDataSource,
    private val mapper: _AssetShortInfoMapper
) : AssetRepository {

    override fun getAssets(): Observable<List<AssetShortInfo>> {
        return source.getAssets()
            .map { it.sortedByDescending(Asset::modificationDate) }
            .map { it.sortedByDescending { it.status != AssetStatus.DRAFT }}
            .flatMapWith(mapper)
            .doOnSubscribe { source.dropCache() }
    }

    override fun getPublisherAvatar(): Observable<String> {
        return source.getPublisher()
            .map(Publisher::smallImageUrl)
            .toObservable()
    }

}