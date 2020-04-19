package com.vmedia.feature.assetlist.domain

import com.vmedia.core.domain.model.AssetShortInfo
import com.vmedia.core.domain.repository.AssetRepository
import io.reactivex.Observable

class AssetListInteractor(
    private val repository: AssetRepository
) {

    fun getPublisherAvatar(): Observable<String> {
        return repository.getPublisherAvatar()
    }

    fun getAssets(): Observable<List<AssetShortInfo>> {
        return repository.getAssets()
    }

}