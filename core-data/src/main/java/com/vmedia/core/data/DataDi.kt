package com.vmedia.core.data

import android.content.Context
import com.google.gson.GsonBuilder
import com.vmedia.core.data.datasource.PreferencesDataSource
import com.vmedia.core.data.internal.TokenProvider
import com.vmedia.core.data.internal.network.UnityApi
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

private const val RETROFIT_GSON = "retrofit_gson"
private const val RETROFIT_XML = "retrofit_xml"

val dataModule = module {
    single { PreferencesDataSource(get()) }
    single { TokenProvider(get()) }

    single {
        androidApplication().getSharedPreferences(
            "248a648a38292ebc637233f00ff34546",
            Context.MODE_PRIVATE
        )
    }

    single { get<Retrofit>().create(UnityApi::class.java) }

    single { GsonBuilder().setLenient().create() }
    single { GsonConverterFactory.create(get()) }
    single { SimpleXmlConverterFactory.create() }
    single { RxJava2CallAdapterFactory.create() }

    factory {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    factory { Dispatcher().apply { maxRequests = BuildConfig.MAX_REQUESTS } }

    factory {
        val tokenProvider = get<TokenProvider>()

        val builder = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val header = "${BuildConfig.COOKIE_TOKEN}=${tokenProvider.token}"

                val request = chain.request()
                    .newBuilder()
                    .addHeader("Cookie", header)
                    .build()

                chain.proceed(request)
            }
            .dispatcher(get())
            .readTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .connectTimeout(180, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
        }

        return@factory builder.build()
    }
}