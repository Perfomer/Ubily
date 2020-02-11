package com.vmedia.core.data

import android.content.Context
import com.google.gson.GsonBuilder
import com.vmedia.core.data.datasource.CredentialsDataSource
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.datasource.SettingsDataSource
import com.vmedia.core.data.datasource.SynchronizationStatusDataSource
import com.vmedia.core.data.internal.TokenProvider
import com.vmedia.core.data.internal.database.UbilyDatabase
import com.vmedia.core.data.internal.network.UnityApi
import com.vmedia.core.data.internal.network.UnityRssApi
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.parameter.parametersOf
import org.koin.dsl.context.ModuleDefinition
import org.koin.dsl.module.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

private const val PREF_CREDENTIALS = "PREF_CREDENTIALS"
private const val PREF_SETTINGS = "PREF_SETTINGS"

val dataModules by lazy {
    listOf(
        preferencesModule,
        dataBaseModule,
        networkModule
    )
}

private val preferencesModule = module {
    single { CredentialsDataSource(get(PREF_CREDENTIALS)) }
    single { SettingsDataSource(get(PREF_SETTINGS)) }
    single { TokenProvider(get()) }

    single(PREF_CREDENTIALS) {
        androidApplication().getSharedPreferences(
            "248a648a38292ebc637233f00ff34546",
            Context.MODE_PRIVATE
        )
    }

    single(PREF_SETTINGS) {
        androidApplication().getSharedPreferences("ubily_settings", Context.MODE_PRIVATE)
    }
}

private val dataBaseModule = module {
    single { DatabaseDataSource() }

    single { UbilyDatabase.getInstance(androidApplication(), "ubily_db") }

    dao { getAssetDao() }
    dao { getEventDao() }
    dao { getSaleDao() }
}

private val networkModule = module {
    single { SynchronizationStatusDataSource() }

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
        val tokenProvider = get<TokenProvider>()

        val builder = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val header = "${BuildConfig.NETWORK_COOKIE_TOKEN}=${tokenProvider.token}"

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

private inline fun <reified T : Any> ModuleDefinition.dao(
    noinline definition: UbilyDatabase.() -> T
) = single { definition.invoke(get()) }