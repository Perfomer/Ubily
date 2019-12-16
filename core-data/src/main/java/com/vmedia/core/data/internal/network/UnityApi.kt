package com.vmedia.core.data.internal.network

import com.vmedia.core.data.internal.network.entity.PeriodsModel
import com.vmedia.core.data.internal.network.entity.TableValuesModel
import com.vmedia.core.data.internal.network.entity.asset.PackageDetailsModel
import com.vmedia.core.data.internal.network.entity.asset.PackagesModel
import com.vmedia.core.data.internal.network.entity.publisher.PublisherModel
import com.vmedia.core.data.internal.network.entity.publisher.PublisherResponseModel
import com.vmedia.core.data.internal.network.entity.publisher.PublisherWrapModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UnityApi {

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
    fun getPayouts(@Path("userId") userId: Long): Single<TableValuesModel>

    @GET("/api/management/packages.json")
    fun getPackages(): Single<PackagesModel>

    @GET("/api/user/overview.json")
    fun getPublisherOverview(): Single<PublisherModel>

    @GET("/api/management/publisher/info/{userId}.json")
    fun getPublisherInfo(@Path("userId") userId: Long): Single<PublisherResponseModel>

    @GET("/api/management/package-version/{versionId}.json")
    fun getPackageVersionInfo(@Path("versionId") versionId: Long): Single<PackageDetailsModel>

}