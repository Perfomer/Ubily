package com.vmedia.core.network.datasource

import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.Filter
import com.vmedia.core.common.util.filterWith
import com.vmedia.core.common.util.mapWith
import com.vmedia.core.network.*
import com.vmedia.core.network.api.UnityApi
import com.vmedia.core.network.api.UnityRssApi
import com.vmedia.core.network.api.entity.PeriodsModel
import com.vmedia.core.network.api.entity.asset.*
import com.vmedia.core.network.api.entity.publisher.PublisherAccountModel
import com.vmedia.core.network.api.entity.publisher.PublisherModel
import com.vmedia.core.network.api.entity.publisher.PublisherResponseModel
import com.vmedia.core.network.api.entity.publisher.PublisherWrapModel
import com.vmedia.core.network.api.entity.rss.RssChannelModel
import com.vmedia.core.network.api.entity.rss.RssModel
import com.vmedia.core.network.entity.*
import com.vmedia.core.network.entity.internal.IncomeDto
import com.vmedia.core.network.entity.internal.ReviewDto
import io.reactivex.Single

internal class NetworkDataSourceImpl(
    private val api: UnityApi,
    private val rssApi: UnityRssApi,

    private val credentials: NetworkCredentialsProvider,

    private val saleMapper: _SaleMapper,
    private val downloadMapper: _DownloadMapper,
    private val incomeMapper: _IncomeMapper,
    private val reviewMapper: _ReviewMapper,
    private val detailedCommentMapper: _DetailedCommentMapper,
    private val assetMapper: _AssetMapper,
    private val assetDetailsMapper: _AssetDetailsMapper,
    private val periodMapper: _PeriodMapper,
    private val publisherMapper: _PublisherMapper,

    private val reviewFilter: Filter<ReviewDto>,
    private val incomeFilter: Filter<IncomeDto>
) : NetworkDataSource {

    override fun getPeriods(): Single<List<Period>> {
        return api.getPeriods(credentials.userId)
            .map(PeriodsModel::periods)
            .map { it.asReversed() }
            .mapWith(periodMapper)
    }

    override fun getDownloads(period: Period): Single<List<DownloadDto>> {
        return api.getDownloads(credentials.userId, period.toString())
            .mapWith(downloadMapper)
    }

    override fun getSales(period: Period): Single<List<SaleDto>> {
        return api.getSales(credentials.userId, period.toString())
            .mapWith(saleMapper)
    }

    override fun getIncome(): Single<List<IncomeDto>> {
        return api.getIncome(credentials.userId)
            .mapWith(incomeMapper)
            .filterWith(incomeFilter)
    }

    override fun getAssets(): Single<List<AssetDto>> {
        return api.getPackages()
            .map(PackagesModel::packages)
            .mapWith(assetMapper)
    }

    override fun getPublisherId(): Single<Long> {
        return api.getPublisherOverview()
            .map(PublisherModel::publisher)
            .map(PublisherAccountModel::id)
    }

    override fun getPublisherInfo(): Single<PublisherDto> {
        return api.getPublisherInfo(credentials.userId)
            .map(PublisherResponseModel::result)
            .map(PublisherWrapModel::publisher)
            .mapWith(publisherMapper)
    }

    override fun getAssetDetails(versionId: Long): Single<AssetDetailsDto> {
        return api.getPackageVersionInfo(versionId)
            .map(PackageDetailsModel::packageModel)
            .map(PackageModel::version)
            .map(PackageVersionFullModel::languagesModel)
            .map(LanguagesModel::englishUsa)
            .mapWith(assetDetailsMapper)
    }

    override fun getReviews(): Single<List<DetailedReviewDto>> {
        val token = credentials.rssToken

        return rssApi.getReviewsRss(token.publisherName, token.token)
            .map(RssModel::getChannel)
            .map(RssChannelModel::getItems)
            .mapWith(reviewMapper)
            .filterWith(reviewFilter)
            .mapWith(detailedCommentMapper)
    }

}