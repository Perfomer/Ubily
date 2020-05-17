package com.vmedia.core.data

import android.content.Context
import androidx.room.RoomDatabase
import com.vmedia.core.common.pure.obj.event.EventInfo
import com.vmedia.core.common.pure.util.ObservableListMapper
import com.vmedia.core.common.pure.util.ObservableMapper
import com.vmedia.core.common.pure.util.toListMapper
import com.vmedia.core.data.datasource.CredentialsDataSource
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.datasource.PublisherDataSource
import com.vmedia.core.data.datasource.SettingsDataSource
import com.vmedia.core.data.datasource.impl.CredentialsDataSourceImpl
import com.vmedia.core.data.datasource.impl.DatabaseDataSourceImpl
import com.vmedia.core.data.datasource.impl.PublisherDataSourceImpl
import com.vmedia.core.data.datasource.impl.SettingsDataSourceImpl
import com.vmedia.core.data.internal.database.UbilyDatabase
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.repository.asset.AssetCacheDatabaseDataSource
import com.vmedia.core.data.repository.asset.AssetRepositoryImpl
import com.vmedia.core.data.repository.asset.mapper.AssetShortInfoMapper
import com.vmedia.core.data.repository.event.EventCacheDatabaseDataSource
import com.vmedia.core.data.repository.event.EventRepositoryImpl
import com.vmedia.core.data.repository.event.mapper.*
import com.vmedia.core.domain.model.AssetShortInfo
import com.vmedia.core.domain.repository.AssetRepository
import com.vmedia.core.domain.repository.EventRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.definition.BeanDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal typealias _AssetShortInfoMapper = ObservableListMapper<Asset, AssetShortInfo>

internal typealias _EventListMapper = ObservableListMapper<Event, EventInfo<*>>
internal typealias _EventMapper = ObservableMapper<Event, EventInfo<*>>
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

val coreDataPreferencesModule = module {
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

val coreDataDatabaseModule = module {
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
            artworkDao = get(),
            keywordDao = get(),
            assetKeywordDao = get(),
            categoryDao = get()
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
    dao { getArtworkDao() }
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
    dao { getCategoryDao() }

    single { EventCacheDatabaseDataSource(get()) }
    single { AssetCacheDatabaseDataSource(get()) }

    single<EventRepository> {
        EventRepositoryImpl(
            source = get(),
            eventListMapper = get<EventMapper>().toListMapper(),
            eventMapper = get<EventMapper>()
        )
    }

    single<AssetRepository> {
        AssetRepositoryImpl(
            source = get(),
            mapper = get<AssetShortInfoMapper>().toListMapper()
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

    single { AssetMapper(get<EventCacheDatabaseDataSource>()) }
    single { DownloadMapper(get<EventCacheDatabaseDataSource>()) }
    single { PayoutMapper(get<EventCacheDatabaseDataSource>()) }
    single { RevenueMapper(get<EventCacheDatabaseDataSource>()) }
    single { ReviewMapper(get<EventCacheDatabaseDataSource>()) }
    single { SaleMapper(get<EventCacheDatabaseDataSource>()) }

    single { AssetShortInfoMapper(get<AssetCacheDatabaseDataSource>()) }
}

private inline fun <reified T : Any> Module.dao(
    noinline definition: UbilyDatabase.() -> T
): BeanDefinition<T> {
    return single { definition.invoke(get()) }
}