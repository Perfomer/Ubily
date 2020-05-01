package com.vmedia.core.network.datasource

import com.vmedia.core.common.pure.obj.Period
import com.vmedia.core.network.api.entity.CategoryDto
import com.vmedia.core.network.entity.*
import com.vmedia.core.network.entity.internal.IncomeDto
import io.reactivex.Single

interface NetworkDataSource {

    fun getPeriods(): Single<List<Period>>

    fun getDownloads(period: Period): Single<List<DownloadDto>>

    fun getSales(period: Period): Single<List<SaleDto>>

    fun getIncome(): Single<List<IncomeDto>>

    fun getAssets(): Single<List<AssetDto>>

    fun getPublisherId(): Single<Long>

    fun getPublisherInfo(): Single<PublisherDto>

    fun getAssetDetails(versionId: Long): Single<AssetDetailsDto>

    fun getReviews(): Single<List<DetailedReviewDto>>

    /**
     * Get categories list
     *
     * @param versionId any asset version id
     */
    fun getCategories(versionId: Long): Single<List<CategoryDto>>

}