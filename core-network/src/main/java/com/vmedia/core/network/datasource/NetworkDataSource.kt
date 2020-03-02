package com.vmedia.core.network.datasource

import com.vmedia.core.common.obj.Period
import com.vmedia.core.network.*
import com.vmedia.core.network.api.UnityApi
import com.vmedia.core.network.api.UnityRssApi
import com.vmedia.core.network.api.entity.DownloadDto
import com.vmedia.core.network.api.entity.RevenueDto
import com.vmedia.core.network.api.entity.SaleDto
import com.vmedia.core.network.api.entity.rest.AssetDetailsDto
import com.vmedia.core.network.api.entity.rest.AssetDto
import com.vmedia.core.network.api.entity.rest.CommentDto
import com.vmedia.core.network.api.entity.rest.PeriodsModel
import com.vmedia.core.network.api.entity.rest.asset.*
import com.vmedia.core.network.api.entity.rest.publisher.PublisherModel
import com.vmedia.core.network.api.entity.rest.publisher.PublisherResponseModel
import com.vmedia.core.network.api.entity.rest.rss.RssChannelModel
import com.vmedia.core.network.api.entity.rest.rss.RssModel
import com.vmedia.core.network.util.toPeriods
import io.reactivex.Single

class NetworkDataSource(
    private val api: UnityApi,
    private val rssApi: UnityRssApi,

    private val credentials: NetworkCredentialsProvider,

    private val saleMapper: _SaleMapper,
    private val downloadMapper: _DownloadMapper,
    private val revenueMapper: _RevenueMapper,
    private val commentMapper: _CommentMapper,
    private val assetMapper: _AssetMapper,
    private val assetDetailsMapper: _AssetDetailsMapper
) {

    fun getPeriods(): Single<List<Period>> {
        return api.getPeriods(credentials.userId)
            .map(PeriodsModel::toPeriods)
    }

    fun getDownloads(period: Period): Single<List<DownloadDto>> {
        return api.getDownloads(credentials.userId, period.toString())
            .map(downloadMapper::map)
    }

    fun getSales(period: Period): Single<List<SaleDto>> {
        return api.getSales(credentials.userId, period.toString())
            .map(saleMapper::map)
    }

    fun getRevenue(): Single<List<RevenueDto>> {
        return api.getRevenue(credentials.userId)
            .map(revenueMapper::map)
    }

    fun getAssets(): Single<List<AssetDto>> {
        return api.getPackages()
            .map(PackagesModel::packages)
            .map(assetMapper::map)
    }

    fun getPublisherOverview(): Single<PublisherModel> {
        return api.getPublisherOverview()
    }

    fun getPublisherInfo(): Single<PublisherResponseModel> {
        return api.getPublisherInfo(credentials.userId)
    }

    fun getAssetDetails(versionId: Long): Single<AssetDetailsDto> {
        return api.getPackageVersionInfo(versionId)
            .map(PackageDetailsModel::packageModel)
            .map(PackageModel::version)
            .map(PackageVersionFullModel::languagesModel)
            .map(LanguagesModel::englishUsa)
            .map(assetDetailsMapper::map)
    }

    fun getComments(): Single<List<CommentDto>> {
        val token = credentials.rssToken

        return rssApi.getCommentsRss(token.publisherName, token.token)
            .map(RssModel::getChannel)
            .map(RssChannelModel::getItems)
            .map(commentMapper::map)
    }

}