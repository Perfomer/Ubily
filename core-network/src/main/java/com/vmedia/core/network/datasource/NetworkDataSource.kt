package com.vmedia.core.network.datasource

import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.ListMapper
import com.vmedia.core.common.util.Mapper
import com.vmedia.core.network.api.UnityApi
import com.vmedia.core.network.api.UnityRssApi
import com.vmedia.core.network.api.entity.DownloadDto
import com.vmedia.core.network.api.entity.RevenueDto
import com.vmedia.core.network.api.entity.SaleDto
import com.vmedia.core.network.api.entity.rest.AssetDto
import com.vmedia.core.network.api.entity.rest.CommentDto
import com.vmedia.core.network.api.entity.rest.PeriodsModel
import com.vmedia.core.network.api.entity.rest.TableValuesModel
import com.vmedia.core.network.api.entity.rest.asset.PackageDetailsModel
import com.vmedia.core.network.api.entity.rest.asset.PackageModelWithVersions
import com.vmedia.core.network.api.entity.rest.asset.PackagesModel
import com.vmedia.core.network.api.entity.rest.publisher.PublisherModel
import com.vmedia.core.network.api.entity.rest.publisher.PublisherResponseModel
import com.vmedia.core.network.api.entity.rest.rss.RssChannelModel
import com.vmedia.core.network.api.entity.rest.rss.RssItemModel
import com.vmedia.core.network.api.entity.rest.rss.RssModel
import com.vmedia.core.network.util.toPeriods
import io.reactivex.Single

class NetworkDataSource(
    private val api: UnityApi,
    private val rssApi: UnityRssApi,
    private val credentials: NetworkCredentialsProvider,
    private val saleMapper: Mapper<TableValuesModel, List<SaleDto>>,
    private val downloadMapper: Mapper<TableValuesModel, List<DownloadDto>>,
    private val revenueMapper: Mapper<TableValuesModel, List<RevenueDto>>,
    private val commentMapper: ListMapper<RssItemModel, CommentDto>,
    private val assetMapper: ListMapper<PackageModelWithVersions, AssetDto>
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

    fun getPackageVersionInfo(versionId: Long): Single<PackageDetailsModel> {
        return api.getPackageVersionInfo(versionId)
    }

    fun getComments(): Single<List<CommentDto>> {
        val token = credentials.rssToken

        return rssApi.getCommentsRss(token.publisherName, token.token)
            .map(RssModel::getChannel)
            .map(RssChannelModel::getItems)
            .map(commentMapper::map)
    }

}