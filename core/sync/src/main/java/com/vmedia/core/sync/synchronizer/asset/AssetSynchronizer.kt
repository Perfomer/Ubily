package com.vmedia.core.sync.synchronizer.asset

import com.vmedia.core.common.pure.util.rx.actOnSuccess
import com.vmedia.core.common.pure.util.rx.associateWith
import com.vmedia.core.common.pure.util.filterWith
import com.vmedia.core.common.pure.util.mapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync._AssetFilter
import com.vmedia.core.sync._AssetMapper
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class AssetSynchronizer(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,

    private val mapper: _AssetMapper,
    private val filter: _AssetFilter
) : Synchronizer<List<AssetModel>> {

    override val dataType = SynchronizationDataType.ASSETS

    override fun execute(): Single<List<AssetModel>> {
        return networkDataSource.getAssets()
            .filterWith(filter)
            .associateWith { networkDataSource.getAssetDetails(it.packageVersionId) }
            .mapWith(mapper)
            .actOnSuccess(::save)
    }

    private fun save(assets: List<AssetModel>): Completable {
        return Observable.fromIterable(assets)
            .flatMapCompletable { databaseDataSource.putAsset(it.asset, it.artworks, it.keywords) }
    }

}
