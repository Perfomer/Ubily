package com.vmedia.core.network.datasource

import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.Filter
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.network.*
import com.vmedia.core.network.api.UnityApi
import com.vmedia.core.network.api.UnityRssApi
import com.vmedia.core.network.api.entity.*
import com.vmedia.core.network.api.entity.rest.PeriodsModel
import com.vmedia.core.network.api.entity.rest.asset.*
import com.vmedia.core.network.api.entity.rest.publisher.PublisherAccountModel
import com.vmedia.core.network.api.entity.rest.publisher.PublisherModel
import com.vmedia.core.network.api.entity.rest.publisher.PublisherResponseModel
import com.vmedia.core.network.api.entity.rest.publisher.PublisherWrapModel
import com.vmedia.core.network.api.entity.rest.rss.RssChannelModel
import com.vmedia.core.network.api.entity.rest.rss.RssModel
import io.reactivex.Single

class NetworkDataSource(
    private val api: UnityApi,
    private val rssApi: UnityRssApi,

    private val credentials: NetworkCredentialsProvider,

    private val saleMapper: _SaleMapper,
    private val downloadMapper: _DownloadMapper,
    private val revenueMapper: _RevenueMapper,
    private val commentMapper: _CommentMapper,
    private val detailedCommentMapper: _DetailedCommentMapper,
    private val assetMapper: _AssetMapper,
    private val assetDetailsMapper: _AssetDetailsMapper,
    private val periodMapper: _PeriodMapper,
    private val publisherMapper: _PublisherMapper,

    private val commentFilter: Filter<CommentDto>
) {

    fun getPeriods(): Single<List<Period>> {
        return api.getPeriods(credentials.userId)
            .map(PeriodsModel::periods)
            .mapWith(periodMapper)
    }

    fun getDownloads(period: Period): Single<List<DownloadDto>> {
        return api.getDownloads(credentials.userId, period.toString())
            .mapWith(downloadMapper)
    }

    fun getSales(period: Period): Single<List<SaleDto>> {
        return api.getSales(credentials.userId, period.toString())
            .mapWith(saleMapper)
    }

    fun getRevenue(): Single<List<RevenueDto>> {
        return api.getRevenue(credentials.userId)
            .mapWith(revenueMapper)
    }

    fun getAssets(): Single<List<AssetDto>> {
        return api.getPackages()
            .map(PackagesModel::packages)
            .mapWith(assetMapper)
    }

    fun getPublisherId(): Single<Long> {
        return api.getPublisherOverview()
            .map(PublisherModel::publisher)
            .map(PublisherAccountModel::id)
    }

    fun getPublisherInfo(): Single<PublisherDto> {
        return api.getPublisherInfo(credentials.userId)
            .map(PublisherResponseModel::result)
            .map(PublisherWrapModel::publisher)
            .mapWith(publisherMapper)
    }

    fun getAssetDetails(versionId: Long): Single<AssetDetailsDto> {
        return api.getPackageVersionInfo(versionId)
            .map(PackageDetailsModel::packageModel)
            .map(PackageModel::version)
            .map(PackageVersionFullModel::languagesModel)
            .map(LanguagesModel::englishUsa)
            .mapWith(assetDetailsMapper)
    }

    fun getComments(): Single<List<DetailedCommentDto>> {
        val token = credentials.rssToken

        return rssApi.getCommentsRss(token.publisherName, token.token)
            .map(RssModel::getChannel)
            .map(RssChannelModel::getItems)
            .mapWith(commentMapper)
            .filterWith(commentFilter)
            .mapWith(detailedCommentMapper)
    }

}