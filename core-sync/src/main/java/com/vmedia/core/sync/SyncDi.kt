package com.vmedia.core.sync

import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.*
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.*
import com.vmedia.core.network.entity.*
import com.vmedia.core.network.entity.internal.RevenueEventDto
import com.vmedia.core.sync.cache.CachedDatabaseDataSourceDecorator
import com.vmedia.core.sync.cache.CachedNetworkDataSourceDecorator
import com.vmedia.core.sync.datasource.SynchronizationDataSource
import com.vmedia.core.sync.datasource.SynchronizationDataSourceImpl
import com.vmedia.core.sync.datasource.SynchronizationPeriodsProviderImpl
import com.vmedia.core.sync.synchronizer.MutableSynchronizationPeriodsProvider
import com.vmedia.core.sync.synchronizer.PublisherCredentialsSynchronizer
import com.vmedia.core.sync.synchronizer.SynchronizationPeriodsProvider
import com.vmedia.core.sync.synchronizer.Synchronizer
import com.vmedia.core.sync.synchronizer.asset.AssetFilter
import com.vmedia.core.sync.synchronizer.asset.AssetMapper
import com.vmedia.core.sync.synchronizer.asset.AssetModel
import com.vmedia.core.sync.synchronizer.asset.AssetSynchronizer
import com.vmedia.core.sync.synchronizer.download.DownloadMapper
import com.vmedia.core.sync.synchronizer.download.DownloadSynchronizer
import com.vmedia.core.sync.synchronizer.payout.PayoutDateFilter
import com.vmedia.core.sync.synchronizer.payout.PayoutMapper
import com.vmedia.core.sync.synchronizer.payout.PayoutSynchronizer
import com.vmedia.core.sync.synchronizer.period.PeriodFilter
import com.vmedia.core.sync.synchronizer.period.PeriodSynchronizer
import com.vmedia.core.sync.synchronizer.publisher.PublisherMapper
import com.vmedia.core.sync.synchronizer.publisher.PublisherSynchronizer
import com.vmedia.core.sync.synchronizer.revenue.RevenueDateFilter
import com.vmedia.core.sync.synchronizer.revenue.RevenueMapper
import com.vmedia.core.sync.synchronizer.revenue.RevenueSynchronizer
import com.vmedia.core.sync.synchronizer.review.ReviewFilter
import com.vmedia.core.sync.synchronizer.review.ReviewMapper
import com.vmedia.core.sync.synchronizer.review.ReviewSynchronizer
import com.vmedia.core.sync.synchronizer.sale.SaleFilter
import com.vmedia.core.sync.synchronizer.sale.SaleMapper
import com.vmedia.core.sync.synchronizer.sale.SaleSynchronizer
import com.vmedia.core.sync.synchronizer.user.UserFilter
import com.vmedia.core.sync.synchronizer.user.UserMapper
import com.vmedia.core.sync.synchronizer.user.UserSynchronizer
import org.koin.core.definition.BeanDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.math.BigDecimal
import java.util.*

val syncModules by lazy {
    listOf(syncModule, synchronizerModule, mapperModule, filterModule, providerModule)
}

internal typealias _AssetProviderById = (id: Long) -> Asset?
internal typealias _AssetProviderByName = (name: String) -> Asset
internal typealias _AssetProviderByUrl = (url: String) -> Asset
internal typealias _UserProviderByName = (name: String) -> User
internal typealias _PeriodIdProvider = (period: Period) -> Long
internal typealias _LastSaleDateProvider = (period: Period, assetId: Long, priceUsd: BigDecimal) -> Date?
internal typealias _LastPayoutDateProvider = () -> Date?
internal typealias _LastRevenueDateProvider = () -> Date?
internal typealias _LastPeriodProvider = () -> Period?
internal typealias _FreeDownloadsPeriodsProvider = () -> List<Period>
internal typealias _ReviewProvider = (authorId: Long, assetId: Long) -> Review?

internal typealias _AssetMapper = ListMapper<Pair<AssetDto, AssetDetailsDto>, AssetModel>
internal typealias _SaleMapper = ListMapper<SaleDto, Sale>
internal typealias _DownloadMapper = ListMapper<DownloadDto, Sale>
internal typealias _RevenueMapper = ListMapper<RevenueEventDto.Revenue, Revenue>
internal typealias _PayoutMapper = ListMapper<RevenueEventDto.Payout, Payout>
internal typealias _ReviewMapper = ListMapper<DetailedReviewDto, Review>
internal typealias _UserMapper = ListMapper<DetailedReviewDto, User>
internal typealias _PublisherMapper = Mapper<Pair<Long, PublisherDto>, Publisher>

internal typealias _AssetFilter = Filter<AssetDto>
internal typealias _SaleFilter = Filter<Sale>
internal typealias _RevenueFilter = Filter<RevenueEventDto.Revenue>
internal typealias _PayoutFilter = Filter<RevenueEventDto.Payout>
internal typealias _PeriodFilter = Filter<Period>
internal typealias _ReviewFilter = Filter<Review>
internal typealias _UserFilter = Filter<DetailedReviewDto>

internal typealias _AssetSynchronizer = Synchronizer<List<AssetModel>>
internal typealias _PublisherSynchronizer = Synchronizer<Publisher>
internal typealias _ReviewSynchronizer = Synchronizer<List<Review>>
internal typealias _RevenueSynchronizer = Synchronizer<List<Revenue>>
internal typealias _PayoutSynchronizer = Synchronizer<List<Payout>>
internal typealias _SaleSynchronizer = Synchronizer<List<Sale>>
internal typealias _DownloadSynchronizer = Synchronizer<List<Sale>>
internal typealias _PeriodSynchronizer = Synchronizer<List<Period>>
internal typealias _UserSynchronizer = Synchronizer<List<User>>

private const val BEAN_PROVIDER_ASSET_BY_ID = "SyncAssetProviderById"
private const val BEAN_PROVIDER_ASSET_BY_URL = "SyncAssetProviderByUrl"
private const val BEAN_PROVIDER_USER_BY_NAME = "SyncUserProviderByName"
private const val BEAN_PROVIDER_SALE_DATE_LAST = "SyncLastSaleDateProvider"
private const val BEAN_PROVIDER_PAYOUT_DATE_LAST = "SyncLastPayoutDateProvider"
private const val BEAN_PROVIDER_REVENUE_DATE_LAST = "SyncLastRevenueDateProvider"
private const val BEAN_PROVIDER_PERIOD_ID = "SyncPeriodIdProvider"
private const val BEAN_PROVIDER_PERIOD_LAST = "SyncLastPeriodProvider"
private const val BEAN_PROVIDER_REVIEW = "SyncReviewProvider"
private const val BEAN_PROVIDER_PERIODS_FREEDOWNLOADS = "SyncFreeDownloadsPeriodsProvider"

private val syncModule = module {
    single { CachedDatabaseDataSourceDecorator(get()) }
    single { CachedNetworkDataSourceDecorator(get()) }

    single<MutableSynchronizationPeriodsProvider> { SynchronizationPeriodsProviderImpl }
    single<SynchronizationPeriodsProvider> { get<MutableSynchronizationPeriodsProvider>() }

    single<SynchronizationDataSource> {
        SynchronizationDataSourceImpl(
            networkDataSource = get(),
            databaseDataSource = get(),
            synchronizationDataTypeProvider = get(),
            periodsProvider = get(),

            credentialsSynchronizer = get(),

            publisherSynchronizer = get<PublisherSynchronizer>(),
            assetSynchronizer = get<AssetSynchronizer>(),
            downloadSynchronizer = get<DownloadSynchronizer>(),
            payoutSynchronizer = get<PayoutSynchronizer>(),
            periodSynchronizer = get<PeriodSynchronizer>(),
            revenueSynchronizer = get<RevenueSynchronizer>(),
            reviewSynchronizer = get<ReviewSynchronizer>(),
            saleSynchronizer = get<SaleSynchronizer>(),
            userSynchronizer = get<UserSynchronizer>()
        )
    }

    single {
        PublisherCredentialsSynchronizer(
            networkDataSource = get<CachedNetworkDataSourceDecorator>(),
            credentials = get()
        )
    }
}

private val synchronizerModule = module {
    single {
        AssetSynchronizer(
            networkDataSource = get<CachedNetworkDataSourceDecorator>(),
            databaseDataSource = get<CachedDatabaseDataSourceDecorator>(),
            mapper = get<AssetMapper>().toListMapper(),
            filter = get<AssetFilter>()
        )
    }

    single {
        PublisherSynchronizer(
            networkDataSource = get<CachedNetworkDataSourceDecorator>(),
            databaseDataSource = get<CachedDatabaseDataSourceDecorator>(),
            mapper = get<PublisherMapper>()
        )
    }

    single {
        SaleSynchronizer(
            networkDataSource = get<CachedNetworkDataSourceDecorator>(),
            databaseDataSource = get<CachedDatabaseDataSourceDecorator>(),
            periodsProvider = get(),
            mapper = get<SaleMapper>().toListMapper(),
            filter = get<SaleFilter>()
        )
    }

    single {
        RevenueSynchronizer(
            networkDataSource = get<CachedNetworkDataSourceDecorator>(),
            databaseDataSource = get<CachedDatabaseDataSourceDecorator>(),
            mapper = get<RevenueMapper>().toListMapper(),
            filter = get<RevenueDateFilter>()
        )
    }

    single {
        PayoutSynchronizer(
            networkDataSource = get<CachedNetworkDataSourceDecorator>(),
            databaseDataSource = get<CachedDatabaseDataSourceDecorator>(),
            mapper = get<PayoutMapper>().toListMapper(),
            filter = get<PayoutDateFilter>()
        )
    }

    single {
        PeriodSynchronizer(
            networkDataSource = get<CachedNetworkDataSourceDecorator>(),
            databaseDataSource = get<CachedDatabaseDataSourceDecorator>(),
            filter = get<PeriodFilter>()
        )
    }

    single {
        UserSynchronizer(
            networkDataSource = get<CachedNetworkDataSourceDecorator>(),
            databaseDataSource = get<CachedDatabaseDataSourceDecorator>(),
            mapper = get<UserMapper>().toListMapper(),
            filter = get<UserFilter>()
        )
    }

    single {
        ReviewSynchronizer(
            networkDataSource = get<CachedNetworkDataSourceDecorator>(),
            databaseDataSource = get<CachedDatabaseDataSourceDecorator>(),
            mapper = get<ReviewMapper>().toListMapper(),
            filter = get<ReviewFilter>()
        )
    }

    single {
        DownloadSynchronizer(
            networkDataSource = get<CachedNetworkDataSourceDecorator>(),
            databaseDataSource = get<CachedDatabaseDataSourceDecorator>(),
            periodsProvider = get(),
            freePeriodsProvider = get(named(BEAN_PROVIDER_PERIODS_FREEDOWNLOADS)),
            mapper = get<DownloadMapper>().toListMapper(),
            filter = get<SaleFilter>()
        )
    }
}

private val mapperModule = module {
    single { AssetMapper }
    single { UserMapper }
    single { PublisherMapper }
    single { SaleMapper(get(named(BEAN_PROVIDER_ASSET_BY_URL))) }
    single { DownloadMapper(get(named(BEAN_PROVIDER_ASSET_BY_URL))) }
    single { RevenueMapper(get(named(BEAN_PROVIDER_PERIOD_ID))) }
    single { PayoutMapper(get(named(BEAN_PROVIDER_PERIOD_ID))) }
    single {
        ReviewMapper(
            assetProvider = get(named(BEAN_PROVIDER_ASSET_BY_URL)),
            userProvider = get(named(BEAN_PROVIDER_USER_BY_NAME))
        )
    }
}

private val filterModule = module {
    single { PeriodFilter(get(named(BEAN_PROVIDER_PERIOD_LAST))) }
    single { AssetFilter(get(named(BEAN_PROVIDER_ASSET_BY_ID))) }
    single { SaleFilter(get(), get(named(BEAN_PROVIDER_SALE_DATE_LAST))) }
    single { ReviewFilter(get(named(BEAN_PROVIDER_REVIEW))) }
    single { UserFilter }
    single { RevenueDateFilter(get(named(BEAN_PROVIDER_REVENUE_DATE_LAST))) }
    single { PayoutDateFilter(get(named(BEAN_PROVIDER_PAYOUT_DATE_LAST))) }
}

private val providerModule = module {
    databaseProvider<_AssetProviderById>(named(BEAN_PROVIDER_ASSET_BY_ID)) {
        { id: Long -> getAsset(id).blockingNullable() }
    }

    databaseProvider<_AssetProviderByUrl>(named(BEAN_PROVIDER_ASSET_BY_URL)) {
        { url: String -> getAssetByUrl(url).blockingGet() }
    }

    databaseProvider<_UserProviderByName>(named(BEAN_PROVIDER_USER_BY_NAME)) {
        { name: String -> getUserByName(name).blockingGet() }
    }

    databaseProvider<_LastSaleDateProvider>(named(BEAN_PROVIDER_SALE_DATE_LAST)) {
        { period: Period, assetId: Long, priceUsd: BigDecimal ->
            getLastSale(assetId, period, priceUsd)
                .map(Sale::date)
                .blockingNullable()
        }
    }

    databaseProvider<_LastPayoutDateProvider>(named(BEAN_PROVIDER_PAYOUT_DATE_LAST)) {
        { getLastPayout().map(Payout::date).blockingNullable() }
    }

    databaseProvider<_LastRevenueDateProvider>(named(BEAN_PROVIDER_REVENUE_DATE_LAST)) {
        { getLastRevenue().map(Revenue::date).blockingNullable() }
    }

    databaseProvider<_LastPeriodProvider>(named(BEAN_PROVIDER_PERIOD_LAST)) {
        { getLastPeriod().blockingNullable() }
    }

    databaseProvider<_ReviewProvider>(named(BEAN_PROVIDER_REVIEW)) {
        { authorId: Long, assetId: Long -> getReview(authorId, assetId).blockingNullable() }
    }

    databaseProvider<_PeriodIdProvider>(named(BEAN_PROVIDER_PERIOD_ID)) {
        { period: Period -> getPeriodId(period).blockingGet() }
    }

    databaseProvider<_FreeDownloadsPeriodsProvider>(named(BEAN_PROVIDER_PERIODS_FREEDOWNLOADS)) {
        { getFreeDownloadsPeriods().blockingGet() }
    }
}

private inline fun <reified T : Any> Module.databaseProvider(
    qualifier: Qualifier? = null,
    noinline definition: DatabaseDataSource.() -> T
): BeanDefinition<T> {
    return single(qualifier) {
        definition.invoke(get<CachedDatabaseDataSourceDecorator>())
    }
}