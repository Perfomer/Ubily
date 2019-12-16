package com.vmedia.core.data.internal.network

import com.vmedia.core.data.internal.network.entity.PeriodRequest
import com.vmedia.core.data.internal.network.entity.TableValuesModel
import com.vmedia.core.data.internal.network.entity.asset.PackageDetailsRequest
import com.vmedia.core.data.internal.network.entity.asset.PackageRequest
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UnityApi {

    @GET("/api/publisher-info/months/{userId}.json")
    fun getPeriods(@Path("userId") userId: Long): Single<PeriodRequest>

    @GET("/api/publisher-info/downloads/{userId}/{period}.json")
    fun getFreeDownloads(
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
    fun getPackages(): Single<PackageRequest>

//    @GET("/api/user/overview.json")
//    fun getPublisherOverview(): Single<PublisherOverviewModel>
//
//    @GET("/api/management/publisher/info/{userId}.json")
//    fun getPublisherInfo(@Path("userId") userId: Long): Single<Request<PublisherRequest>>

    @GET("/api/management/package-version/{versionId}.json")
    fun getPackageVersionInfo(@Path("versionId") versionId: Long): Single<PackageDetailsRequest>

    @GET("/feed/{publisherActivityName}/{publisherActivityToken}/activity.rss")
    fun getCommentsRss(
        @Path("publisherActivityName") userName: String,
        @Path("publisherActivityToken") token: String
    ): Single<String>

}