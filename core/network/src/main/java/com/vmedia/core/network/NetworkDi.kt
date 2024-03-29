package com.vmedia.core.network

import com.google.gson.GsonBuilder
import com.vmedia.core.common.pure.obj.Period
import com.vmedia.core.common.pure.util.ListMapper
import com.vmedia.core.common.pure.util.Mapper
import com.vmedia.core.common.pure.util.toListMapper
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
import com.vmedia.core.network.entity.internal.IncomeDto
import com.vmedia.core.network.entity.internal.ReviewDto
import com.vmedia.core.network.filter.RevenueFilter
import com.vmedia.core.network.filter.ReviewFilter
import com.vmedia.core.network.mapper.*
import com.vmedia.core.network.util.Cookie
import com.vmedia.core.network.util.addCookieInterceptor
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

internal typealias _SaleMapper = Mapper<TableValuesModel, List<SaleDto>>
internal typealias _DownloadMapper = Mapper<TableValuesModel, List<DownloadDto>>
internal typealias _IncomeMapper = Mapper<TableValuesModel, List<IncomeDto>>
internal typealias _DetailedCommentMapper = Mapper<List<ReviewDto>, List<DetailedReviewDto>>
internal typealias _AssetDetailsMapper = Mapper<LanguageMetadataModel, AssetDetailsDto>
internal typealias _PublisherMapper = Mapper<PublisherDetailsModel, PublisherDto>
internal typealias _AssetMapper = ListMapper<PackageModelWithVersions, AssetDto>
internal typealias _ReviewMapper = ListMapper<RssItemModel, ReviewDto>
internal typealias _PeriodMapper = ListMapper<PeriodModel, Period>

val coreNetworkModule = module {
    single<NetworkDataSource> {
        NetworkDataSourceImpl(
            api = get(),
            rssApi = get(),
            credentials = get(),
            saleMapper = get<SaleMapper>(),
            downloadMapper = get<DownloadMapper>(),
            incomeMapper = get<IncomeMapper>(),
            periodMapper = get<PeriodMapper>().toListMapper(),
            reviewMapper = get<ReviewMapper>().toListMapper(),
            assetMapper = get<AssetMapper>().toListMapper(),
            assetDetailsMapper = get<AssetDetailsMapper>(),
            publisherMapper = get<PublisherMapper>(),
            detailedCommentMapper = get<DetailedReviewMapper>(),
            reviewFilter = get<ReviewFilter>(),
            incomeFilter = get<RevenueFilter>()
        )
    }
}

val coreNetworkUtilsModule = module {
    single { SaleMapper }
    single { DownloadMapper }
    single { IncomeMapper }
    single { AssetDetailsMapper }
    single { PublisherMapper }
    single { DetailedReviewMapper }
    single { AssetMapper }
    single { ReviewMapper }
    single { PeriodMapper }

    single { ReviewFilter }
    single { RevenueFilter }
}

val coreNetworkRetrofitModule = module {
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
                    Cookie(BuildConfig.NETWORK_COOKIE_TOKEN, tokenProvider.token.tokenValue),
                    Cookie(BuildConfig.NETWORK_COOKIE_SESSION, tokenProvider.token.session)
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