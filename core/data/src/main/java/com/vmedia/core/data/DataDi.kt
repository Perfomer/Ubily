package com.vmedia.core.data

import android.content.Context
import androidx.room.RoomDatabase
import com.vmedia.core.common.util.ObservableListMapper
import com.vmedia.core.common.util.ObservableMapper
import com.vmedia.core.data.datasource.CredentialsDataSource
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.datasource.PublisherDataSource
import com.vmedia.core.data.datasource.SettingsDataSource
import com.vmedia.core.data.datasource.impl.CredentialsDataSourceImpl
import com.vmedia.core.data.datasource.impl.DatabaseDataSourceImpl
import com.vmedia.core.data.datasource.impl.PublisherDataSourceImpl
import com.vmedia.core.data.datasource.impl.SettingsDataSourceImpl
import com.vmedia.core.data.internal.database.UbilyDatabase
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.obj.EventInfo
import com.vmedia.core.data.repository.event.EventCacheDatabaseDataSource
import com.vmedia.core.data.repository.event.EventRepositoryImpl
import com.vmedia.core.data.repository.event.mapper.*
import com.vmedia.core.domain.repository.EventRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.definition.BeanDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModules by lazy {
    listOf(
        preferencesModule,
        databaseModule
    )
}

internal typealias _EventMapper = ObservableListMapper<Event, EventInfo<*>>
internal typealias _SaleMapper = _EventInfoMapper<EventInfo.EventListInfo.EventSale>
internal typealias _DownloadMapper = _EventInfoMapper<EventInfo.EventListInfo.EventFreeDownload>
internal typealias _AssetMapper = _EventInfoMapper<EventInfo.EventListInfo.EventAsset>
internal typealias _ReviewMapper = _EventInfoMapper<EventInfo.EventReview>
internal typealias _RevenueMapper = _EventInfoMapper<EventInfo.EventRevenue>
internal typealias _PayoutMapper = _EventInfoMapper<EventInfo.EventPayout>

private typealias _EventInfoMapper<T> = ObservableMapper<Event, T>

private const val BEAN_PREF_CREDENTIALS = "PREF_CREDENTIALS"
private const val BEAN_PREF_SETTINGS = "PREF_SETTINGS"
private const val BEAN_PREF_PUBLISHER = "PREF_PUBLISHER"

private val preferencesModule = module {
    single<CredentialsDataSource> {
        CredentialsDataSourceImpl(get(named(BEAN_PREF_CREDENTIALS)), get())
    }

    single<SettingsDataSource> {
        SettingsDataSourceImpl(get(named(BEAN_PREF_SETTINGS)))
    }

    single<PublisherDataSource> {
        PublisherDataSourceImpl(get(named(BEAN_PREF_PUBLISHER)))
    }

    single(named(BEAN_PREF_CREDENTIALS)) {
        androidApplication().getSharedPreferences(
            "248a648a38292ebc637233f00ff34546",
            Context.MODE_PRIVATE
        )
    }

    single(named(BEAN_PREF_SETTINGS)) {
        androidApplication().getSharedPreferences("ubily_settings", Context.MODE_PRIVATE)
    }

    single(named(BEAN_PREF_PUBLISHER)) {
        androidApplication().getSharedPreferences("publisher", Context.MODE_PRIVATE)
    }
}

private val databaseModule = module {
    single<DatabaseDataSource> {
        DatabaseDataSourceImpl(
            database = get(),

            eventDao = get(),
            eventEntityDao = get(),
            publisherDao = get(),
            userDao = get(),
            saleDao = get(),
            periodDao = get(),
            revenueDao = get(),
            payoutDao = get(),
            reviewDao = get(),
            assetDao = get(),
            assetImageDao = get(),
            keywordDao = get(),
            assetKeywordDao = get()
        )
    }

    single<RoomDatabase> { get<UbilyDatabase>() }

    single {
        UbilyDatabase.getInstance(
            appContext = androidContext(),
            databaseName = BuildConfig.DB_NAME,
            inMemory = false
        )
    }

    dao { getAssetDao() }
    dao { getAssetImageDao() }
    dao { getAssetKeywordDao() }
    dao { getKeywordDao() }
    dao { getEventDao() }
    dao { getEventEntityDao() }
    dao { getSaleDao() }
    dao { getPeriodDao() }
    dao { getPublisherDao() }
    dao { getUserDao() }
    dao { getReviewDao() }
    dao { getRevenueDao() }
    dao { getPayoutDao() }

    single { EventCacheDatabaseDataSource(get()) }

    single<EventRepository> {
        EventRepositoryImpl(
            source = get(),
            eventMapper = get<EventMapper>()
        )
    }

    single {
        EventMapper(
            saleMapper = get<SaleMapper>(),
            downloadMapper = get<DownloadMapper>(),
            assetMapper = get<AssetMapper>(),
            reviewMapper = get<ReviewMapper>(),
            revenueMapper = get<RevenueMapper>(),
            payoutMapper = get<PayoutMapper>()
        )
    }

    single { AssetMapper(get()) }
    single { DownloadMapper(get()) }
    single { PayoutMapper(get()) }
    single { RevenueMapper(get()) }
    single { ReviewMapper(get()) }
    single { SaleMapper(get()) }
}

private inline fun <reified T : Any> Module.dao(
    noinline definition: UbilyDatabase.() -> T
): BeanDefinition<T> {
    return single { definition.invoke(get()) }
}