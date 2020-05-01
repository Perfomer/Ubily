package com.vmedia.core.data.repository.asset

import com.vmedia.core.common.android.util.flatMapWith
import com.vmedia.core.data._AssetShortInfoMapper
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
            .flatMapWith(mapper)
            .doOnSubscribe { source.dropCache() }
    }

    override fun getPublisherAvatar(): Observable<String> {
        return source.getPublisher()
            .map(Publisher::smallImageUrl)
            .toObservable()
    }

}