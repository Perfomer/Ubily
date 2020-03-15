package com.vmedia.core.network

import com.google.gson.GsonBuilder
import com.vmedia.core.common.BuildConfig
import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.Filter
import com.vmedia.core.common.util.ListMapper
import com.vmedia.core.common.util.Mapper
import com.vmedia.core.common.util.toListMapper
import com.vmedia.core.network.api.UnityApi
import com.vmedia.core.network.api.UnityRssApi
import com.vmedia.core.network.api.entity.PeriodModel
import com.vmedia.core.network.api.entity.TableValuesModel
import com.vmedia.core.network.api.entity.asset.LanguageMetadataModel
import com.vmedia.core.network.api.entity.asset.PackageModelWithVersions
import com.vmedia.core.network.api.entity.publisher.PublisherDetailsModel
import com.vmedia.core.network.api.entity.rss.RssItemModel
import com.vmedia.core.network.datasource.NetworkCredentialsProvider
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.network.datasource.NetworkDataSourceImpl
import com.vmedia.core.network.entity.*
import com.vmedia.core.network.filter.ReviewFilter
import com.vmedia.core.network.mapper.*
import com.vmedia.core.network.util.addCookieInterceptor
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

internal typealias _SaleMapper = Mapper<TableValuesModel, List<SaleDto>>
internal typealias _DownloadMapper = Mapper<TableValuesModel, List<DownloadDto>>
internal typealias _RevenueMapper = Mapper<TableValuesModel, List<RevenueEventDto>>
internal typealias _DetailedCommentMapper = Mapper<List<ReviewDto>, List<DetailedReviewDto>>
internal typealias _AssetDetailsMapper = Mapper<LanguageMetadataModel, AssetDetailsDto>
internal typealias _PublisherMapper = Mapper<PublisherDetailsModel, PublisherDto>
internal typealias _AssetMapper = ListMapper<PackageModelWithVersions, AssetDto>
internal typealias _ReviewMapper = ListMapper<RssItemModel, ReviewDto>
internal typealias _PeriodMapper = ListMapper<PeriodModel, Period>

val networkModules by lazy {
    listOf(networkModule, utilsModule, retrofitModule)
}

private val networkModule = module {
    single<NetworkDataSource> {
        NetworkDataSourceImpl(
            api = get(),
            rssApi = get(),
            credentials = get(),
            saleMapper = get(BEAN_MAPPER_SALE),
            downloadMapper = get(BEAN_MAPPER_DOWNLOAD),
            revenueMapper = get(BEAN_MAPPER_REVENUE),
            reviewMapper = get(BEAN_MAPPER_REVIEW),
            assetMapper = get(BEAN_MAPPER_ASSET),
            assetDetailsMapper = get(BEAN_MAPPER_ASSETDETAILS),
            periodMapper = get(BEAN_MAPPER_PERIOD),
            publisherMapper = get(BEAN_MAPPER_PUBLISHER),
            reviewFilter = get(BEAN_FILTER_REVIEW),
            detailedCommentMapper = get(BEAN_MAPPER_DETAILEDCOMMENT)
        )
    }
}

private val utilsModule = module {
    single<_SaleMapper>(BEAN_MAPPER_SALE) { SaleMapper }
    single<_DownloadMapper>(BEAN_MAPPER_DOWNLOAD) { DownloadMapper }
    single<_RevenueMapper>(BEAN_MAPPER_REVENUE) { RevenueMapper }
    single<_AssetDetailsMapper>(BEAN_MAPPER_ASSETDETAILS) { AssetDetailsMapper }
    single<_PublisherMapper>(BEAN_MAPPER_PUBLISHER) { PublisherMapper }
    single<_DetailedCommentMapper>(BEAN_MAPPER_DETAILEDCOMMENT) { DetailedReviewMapper }
    single(BEAN_MAPPER_ASSET) { AssetMapper.toListMapper() }
    single(BEAN_MAPPER_REVIEW) { ReviewMapper.toListMapper() }
    single(BEAN_MAPPER_PERIOD) { PeriodMapper.toListMapper() }

    single<Filter<ReviewDto>>(BEAN_FILTER_REVIEW) { ReviewFilter }
}

private val retrofitModule = module {
    single {
        val get = get<Retrofit> { parametersOf(get<GsonConverterFactory>()) }
        get.create(UnityApi::class.java)
    }

    single {
        val get = get<Retrofit> { parametersOf(get<SimpleXmlConverterFactory>()) }
        get.create(UnityRssApi::class.java)
    }

    single { GsonBuilder().setLenient().create() }
    single { GsonConverterFactory.create(get()) }
    single { SimpleXmlConverterFactory.create() }
    single { RxJava2CallAdapterFactory.create() }

    factory { (converterFactory: Converter.Factory) ->
        Retrofit.Builder()
            .baseUrl(BuildConfig.NETWORK_BASE_URL)
            .client(get())
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .addConverterFactory(converterFactory)
            .build()
    }

    factory { Dispatcher().apply { maxRequests = BuildConfig.NETWORK_MAX_REQUESTS } }

    factory {
        val tokenProvider = get<NetworkCredentialsProvider>()

        val builder = OkHttpClient.Builder()
            .addCookieInterceptor {
                arrayOf(
                    BuildConfig.NETWORK_COOKIE_TOKEN to tokenProvider.token.tokenValue,
                    BuildConfig.NETWORK_COOKIE_SESSION to tokenProvider.token.session
                )
            }
            .dispatcher(get())
            .readTimeout(BuildConfig.NETWORK_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(BuildConfig.NETWORK_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(BuildConfig.NETWORK_CONNECT_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
        }

        return@factory builder.build()
    }
}

private const val BEAN_MAPPER_SALE = "NetworkSaleMapper"
private const val BEAN_MAPPER_DOWNLOAD = "NetworkDownloadMapper"
private const val BEAN_MAPPER_REVENUE = "NetworkRevenueMapper"
private const val BEAN_MAPPER_DETAILEDCOMMENT = "NetworkDetailedCommentMapper"
private const val BEAN_MAPPER_REVIEW = "NetworkReviewMapper"
private const val BEAN_MAPPER_ASSET = "NetworkAssetMapper"
private const val BEAN_MAPPER_ASSETDETAILS = "NetworkAssetDetailsMapper"
private const val BEAN_MAPPER_PUBLISHER = "NetworkPublisherMapper"
private const val BEAN_MAPPER_PERIOD = "NetworkPeriodMapper"

private const val BEAN_FILTER_REVIEW = "NetworkReviewFilter"