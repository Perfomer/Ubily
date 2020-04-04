package com.vmedia.core.sync.synchronizer.asset

import com.vmedia.core.common.util.actOnSuccess
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.sync.SynchronizationDataType
import com.vmedia.core.sync._AssetFilter
import com.vmedia.core.sync._AssetMapper
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith

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
            .flatMapCompletable { databaseDataSource.putAsset(it.asset, it.images, it.keywords) }
    }

    private companion object {

        private fun <T1 : Any, T2 : Any> Single<List<T1>>.associateWith(
            companionSource: (T1) -> Single<T2>
        ): Single<List<Pair<T1, T2>>> {
            return this
                .flatMapObservable {
                    Observable
                        .fromIterable(it)
                        .zipWith(companionSource::invoke)
                }
                .toList()
        }

        private fun <T1 : Any, T2 : Any> Observable<T1>.zipWith(
            companionSource: (T1) -> Single<T2>
        ): Observable<Pair<T1, T2>> {
            return flatMapSingle { Single.just(it).zipWith(companionSource.invoke(it)) }
        }

    }

}