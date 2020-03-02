package com.vmedia.core.network.datasource

import com.vmedia.core.common.obj.Period
import com.vmedia.core.network.api.entity.*
import io.reactivex.Single

interface NetworkDataSource {

    fun getPeriods(): Single<List<Period>>

    fun getDownloads(period: Period): Single<List<DownloadDto>>

    fun getSales(period: Period): Single<List<SaleDto>>

    fun getRevenue(): Single<List<RevenueDto>>

    fun getAssets(): Single<List<AssetDto>>

    fun getPublisherId(): Single<Long>

    fun getPublisherInfo(): Single<PublisherDto>

    fun getAssetDetails(versionId: Long): Single<AssetDetailsDto>

    fun getComments(): Single<List<DetailedCommentDto>>

}