package com.vmedia.core.network

import com.google.gson.GsonBuilder
import com.vmedia.core.common.BuildConfig
import com.vmedia.core.common.util.Mapper
import com.vmedia.core.network.api.UnityApi
import com.vmedia.core.network.api.UnityRssApi
import com.vmedia.core.network.api.entity.DownloadDto
import com.vmedia.core.network.api.entity.RevenueDto
import com.vmedia.core.network.api.entity.SaleDto
import com.vmedia.core.network.api.entity.rest.TableValuesModel
import com.vmedia.core.network.datasource.NetworkCredentialsProvider
import com.vmedia.core.network.datasource.NetworkDataSource
import com.vmedia.core.network.datasource.SynchronizationStatusDataSource
import com.vmedia.core.network.mapper.DownloadMapper
import com.vmedia.core.network.mapper.RevenueMapper
import com.vmedia.core.network.mapper.SaleMapper
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

val networkModule = module {
    single { SynchronizationStatusDataSource() }
    single {
        NetworkDataSource(
            api = get(),
            rssApi = get(),
            credentials = get(),
            saleMapper = get(BEAN_MAPPER_SALE),
            downloadMapper = get(BEAN_MAPPER_DOWNLOAD),
            revenueMapper = get(BEAN_MAPPER_REVENUE)
        )
    }

    single<Mapper<TableValuesModel, List<SaleDto>>>(BEAN_MAPPER_SALE) { SaleMapper }
    single<Mapper<TableValuesModel, List<DownloadDto>>>(BEAN_MAPPER_DOWNLOAD) { DownloadMapper }
    single<Mapper<TableValuesModel, List<RevenueDto>>>(BEAN_MAPPER_REVENUE) { RevenueMapper }

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