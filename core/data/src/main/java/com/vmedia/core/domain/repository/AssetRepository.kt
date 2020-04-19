package com.vmedia.core.domain.repository

import com.vmedia.core.domain.model.AssetShortInfo
import io.reactivex.Observable

interface AssetRepository {

    fun getAssets(): Observable<List<AssetShortInfo>>

    fun getPublisherAvatar(): Observable<String>

}