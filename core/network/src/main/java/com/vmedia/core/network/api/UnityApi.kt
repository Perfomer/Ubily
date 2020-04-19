package com.vmedia.core.network.api

import com.vmedia.core.network.api.entity.CategoryDto
import com.vmedia.core.network.api.entity.PeriodsModel
import com.vmedia.core.network.api.entity.TableValuesModel
import com.vmedia.core.network.api.entity.asset.PackageDetailsModel
import com.vmedia.core.network.api.entity.asset.PackagesModel
import com.vmedia.core.network.api.entity.publisher.PublisherModel
import com.vmedia.core.network.api.entity.publisher.PublisherResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

internal interface UnityApi {

    @GET("/api/publisher-info/months/{userId}.json")
    fun getPeriods(@Path("userId") userId: Long): Single<PeriodsModel>

    @GET("/api/publisher-info/downloads/{userId}/{period}.json")
    fun getDownloads(
        @Path("userId") userId: Long,
        @Path("period") period: String
    ): Single<TableValuesModel>

    @GET("/api/publisher-info/sales/{userId}/{period}.json")
    fun getSales(
        @Path("userId") userId: Long,
        @Path("period") period: String
    ): Single<TableValuesModel>

    @GET("/api/publisher-info/revenue/{userId}.json")
    fun getIncome(@Path("userId") userId: Long): Single<TableValuesModel>

    @GET("/api/management/packages.json")
    fun getPackages(): Single<PackagesModel>

    @GET("/api/user/overview.json")
    fun getPublisherOverview(): Single<PublisherModel>

    @GET("/api/management/publisher/info/{userId}.json")
    fun getPublisherInfo(@Path("userId") userId: Long): Single<PublisherResponseModel>

    @GET("/api/management/package-version/{versionId}.json")
    fun getPackageVersionInfo(@Path("versionId") versionId: Long): Single<PackageDetailsModel>

    @GET("/api/management/categories/185235.json")
    fun getCategories(): Single<List<CategoryDto>>

}