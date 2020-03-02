package com.vmedia.core.network

import com.google.gson.GsonBuilder
import com.vmedia.core.common.BuildConfig
import com.vmedia.core.common.util.ListMapper
import com.vmedia.core.common.util.Mapper
import com.vmedia.core.common.util.toListMapper
import com.vmedia.core.network.api.UnityApi
import com.vmedia.core.network.api.UnityRssApi
import com.vmedia.core.network.api.entity.*
import com.vmedia.core.network.api.entity.rest.TableValuesModel
import com.vmedia.core.network.api.entity.rest.asset.LanguageMetadataModel
import com.vmedia.core.network.api.entity.rest.asset.PackageModelWithVersions
import com.vmedia.core.network.api.entity.rest.rss.RssItemModel
import com.vmedia.core.network.datasource.NetworkCredentialsProvider
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.network.datasource.SynchronizationStatusDataSource
import com.vmedia.core.network.mapper.*
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
internal typealias _RevenueMapper = Mapper<TableValuesModel, List<RevenueDto>>
internal typealias _AssetDetailsMapper = Mapper<LanguageMetadataModel, AssetDetailsDto>
internal typealias _AssetMapper = ListMapper<PackageModelWithVersions, AssetDto>
internal typealias _CommentMapper = ListMapper<RssItemModel, CommentDto>

val networkModule = module {
    single { SynchronizationStatusDataSource() }
    single {
        NetworkDataSource(
            api = get(),
            rssApi = get(),
            credentials = get(),
            saleMapper = get(BEAN_MAPPER_SALE),
            downloadMapper = get(BEAN_MAPPER_DOWNLOAD),
            revenueMapper = get(BEAN_MAPPER_REVENUE),
            commentMapper = get(BEAN_MAPPER_COMMENT),
            assetMapper = get(BEAN_MAPPER_ASSET),
            assetDetailsMapper = get(BEAN_MAPPER_ASSETDETAILS)
        )
    }

    single<_SaleMapper>(BEAN_MAPPER_SALE) { SaleMapper }
    single<_DownloadMapper>(BEAN_MAPPER_DOWNLOAD) { DownloadMapper }
    single<_RevenueMapper>(BEAN_MAPPER_REVENUE) { RevenueMapper }
    single<_AssetDetailsMapper>(BEAN_MAPPER_ASSETDETAILS) { AssetDetailsMapper }
    single(BEAN_MAPPER_ASSET) { AssetMapper.toListMapper() }
    single(BEAN_MAPPER_COMMENT) { CommentMapper.toListMapper() }

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
            .addInterceptor { chain ->
                val header =
                    "${BuildConfig.NETWORK_COOKIE_TOKEN}=${tokenProvider.token.tokenValue};${BuildConfig.NETWORK_COOKIE_SESSION}=${tokenProvider.token.session}"

                val request = chain.request()
                    .newBuilder()
                    .addHeader("Cookie", header)
                    .build()

                chain.proceed(request)
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

private const val BEAN_MAPPER_SALE = "SaleMapper"
private const val BEAN_MAPPER_DOWNLOAD = "DownloadMapper"
private const val BEAN_MAPPER_REVENUE = "RevenueMapper"
private const val BEAN_MAPPER_COMMENT = "CommentMapper"
private const val BEAN_MAPPER_ASSET = "AssetMapper"
private const val BEAN_MAPPER_ASSETDETAILS = "AssetDetailsMapper"