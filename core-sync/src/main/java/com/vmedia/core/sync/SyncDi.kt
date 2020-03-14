package com.vmedia.core.sync

import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.Filter
import com.vmedia.core.common.util.ListMapper
import com.vmedia.core.common.util.Mapper
import com.vmedia.core.common.util.toListMapper
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.*
import com.vmedia.core.network.entity.*
import com.vmedia.core.sync.SynchronizationEvent.*
import com.vmedia.core.sync.cache.CachedDatabaseDataSourceDecorator
import com.vmedia.core.sync.cache.CachedNetworkDataSourceDecorator
import com.vmedia.core.sync.synchronizer.PublisherCredentialsSynchronizer
import com.vmedia.core.sync.synchronizer.SynchronizationPeriodsProvider
import com.vmedia.core.sync.synchronizer.Synchronizer
import com.vmedia.core.sync.synchronizer.asset.AssetFilter
import com.vmedia.core.sync.synchronizer.asset.AssetMapper
import com.vmedia.core.sync.synchronizer.asset.AssetModel
import com.vmedia.core.sync.synchronizer.asset.AssetSynchronizer
import com.vmedia.core.sync.synchronizer.payout.PayoutInstanceFilter
import com.vmedia.core.sync.synchronizer.payout.PayoutMapper
import com.vmedia.core.sync.synchronizer.payout.PayoutSynchronizer
import com.vmedia.core.sync.synchronizer.period.PeriodFilter
import com.vmedia.core.sync.synchronizer.period.PeriodSynchronizer
import com.vmedia.core.sync.synchronizer.publisher.PublisherMapper
import com.vmedia.core.sync.synchronizer.publisher.PublisherSynchronizer
import com.vmedia.core.sync.synchronizer.revenue.RevenueDateFilter
import com.vmedia.core.sync.synchronizer.revenue.RevenueInstanceFilter
import com.vmedia.core.sync.synchronizer.revenue.RevenueMapper
import com.vmedia.core.sync.synchronizer.revenue.RevenueSynchronizer
import com.vmedia.core.sync.synchronizer.review.ReviewFilter
import com.vmedia.core.sync.synchronizer.review.ReviewMapper
import com.vmedia.core.sync.synchronizer.review.ReviewSynchronizer
import com.vmedia.core.sync.synchronizer.sale.SaleFilter
import com.vmedia.core.sync.synchronizer.sale.SaleMapper
import com.vmedia.core.sync.synchronizer.sale.SaleSynchronizer
import com.vmedia.core.sync.synchronizer.user.UserMapper
import com.vmedia.core.sync.synchronizer.user.UserSynchronizer
import org.koin.dsl.module.module
import java.math.BigDecimal
import java.util.*

val syncModules by lazy {
    listOf(syncModule, synchronizerModule, mapperModule, filterModule, providerModule)
}

internal typealias _AssetProviderById = (id: Long) -> Asset
internal typealias _AssetProviderByName = (name: String) -> Asset
internal typealias _AssetProviderByUrl = (url: String) -> Asset
internal typealias _UserProviderByName = (name: String) -> User
internal typealias _LastSaleDateProvider = (period: Period, assetId: Long, priceUsd: BigDecimal) -> Date
internal typealias _LastCreditDateProvider = () -> Date
internal typealias _LastPeriodProvider = () -> Period?
internal typealias _PeriodIdProvider = (period: Period) -> Long
internal typealias _ReviewProvider = (authorId: Long, assetId: Long) -> Review?

internal typealias _AssetMapper = ListMapper<Pair<AssetDto, AssetDetailsDto>, AssetModel>
internal typealias _SaleMapper = ListMapper<SaleDto, Sale>
internal typealias _RevenueMapper = ListMapper<RevenueEventDto, Revenue>
internal typealias _PayoutMapper = ListMapper<RevenueEventDto, Payout>
internal typealias _ReviewMapper = ListMapper<DetailedReviewDto, Review>
internal typealias _UserMapper = ListMapper<DetailedReviewDto, User>
internal typealias _PublisherMapper = Mapper<Pair<Long, PublisherDto>, Publisher>

internal typealias _AssetFilter = Filter<AssetDto>
internal typealias _SaleFilter = Filter<Sale>
internal typealias _RevenueFilter = Filter<RevenueEventDto>
internal typealias _PeriodFilter = Filter<Period>
internal typealias _ReviewFilter = Filter<Review>

internal typealias _AssetSynchronizer = Synchronizer<AssetsReceived>
internal typealias _PublisherSynchronizer = Synchronizer<PublisherReceived>
internal typealias _ReviewSynchronizer = Synchronizer<ReviewsReceived>
internal typealias _RevenueSynchronizer = Synchronizer<RevenuesReceived>
internal typealias _PayoutSynchronizer = Synchronizer<PayoutsReceived>
internal typealias _SaleSynchronizer = Synchronizer<SalesReceived>
internal typealias _DownloadSynchronizer = Synchronizer<FreeDownloadsReceived>
internal typealias _PeriodSynchronizer = Synchronizer<PeriodsReceived>
internal typealias _UserSynchronizer = Synchronizer<PeriodsReceived>

private const val BEAN_CACHED_DATABASE_DATASOURCE = "SyncCachedDatabaseDataSource"
private const val BEAN_CACHED_NETWORK_DATASOURCE = "SyncCachedNetworkDataSource"

private const val BEAN_MAPPER_ASSET = "SyncAssetMapper"
private const val BEAN_MAPPER_SALE = "SyncSaleMapper"
private const val BEAN_MAPPER_PUBLISHER = "SyncPublisherMapper"
private const val BEAN_MAPPER_REVENUE = "SyncRevenueMapper"
private const val BEAN_MAPPER_PAYOUT = "SyncPayoutMapper"
private const val BEAN_MAPPER_REVIEW = "SyncReviewMapper"
private const val BEAN_MAPPER_USER = "SyncUserMapper"

private const val BEAN_FILTER_ASSET = "SyncAssetFilter"
private const val BEAN_FILTER_SALE = "SyncSaleFilter"
private const val BEAN_FILTER_REVENUE_DATE = "SyncRevenueDateFilter"
private const val BEAN_FILTER_REVENUE_INSTANCE = "SyncRevenueInstanceFilter"
private const val BEAN_FILTER_PAYOUT_INSTANCE = "SyncPayoutInstanceFilter"
private const val BEAN_FILTER_PERIOD = "SyncPeriodFilter"
private const val BEAN_FILTER_REVIEW = "SyncReviewFilter"

private const val BEAN_PROVIDER_ASSET_BY_ID = "SyncAssetProviderById"
private const val BEAN_PROVIDER_ASSET_BY_NAME = "SyncAssetProviderByName"
private const val BEAN_PROVIDER_ASSET_BY_URL = "SyncAssetProviderByUrl"
private const val BEAN_PROVIDER_USER_BY_NAME = "SyncUserProviderByName"
private const val BEAN_PROVIDER_SALE_DATE_LAST = "SyncLastSaleDateProvider"
private const val BEAN_PROVIDER_CREDIT_DATE_LAST = "SyncLastCreditDateProvider"
private const val BEAN_PROVIDER_PERIOD_ID = "SyncPeriodIdProvider"
private const val BEAN_PROVIDER_PERIOD_LAST = "SyncLastPeriodProvider"
private const val BEAN_PROVIDER_REVIEW = "SyncReviewProvider"

private const val BEAN_SYNCHRONIZER_ASSET = "SyncAssetSynchronizer"
private const val BEAN_SYNCHRONIZER_PUBLISHER = "SyncPublisherSynchronizer"
private const val BEAN_SYNCHRONIZER_REVIEW = "SyncReviewSynchronizer"
private const val BEAN_SYNCHRONIZER_REVENUE = "SyncRevenueSynchronizer"
private const val BEAN_SYNCHRONIZER_PAYOUT = "SyncPayoutSynchronizer"
private const val BEAN_SYNCHRONIZER_SALE = "SyncSaleSynchronizer"
private const val BEAN_SYNCHRONIZER_DOWNLOAD = "SyncFreeDownloadSynchronizer"
private const val BEAN_SYNCHRONIZER_PERIOD = "SyncPeriodSynchronizer"
private const val BEAN_SYNCHRONIZER_USER = "SyncUserSynchronizer"

private val syncModule = module {
    single(BEAN_CACHED_DATABASE_DATASOURCE) { CachedDatabaseDataSourceDecorator(get()) }
    single(BEAN_CACHED_NETWORK_DATASOURCE) { CachedNetworkDataSourceDecorator(get()) }

    single { get<SynchronizationDataSource>() as SynchronizationPeriodsProvider }

    single<SynchronizationDataSource> {
        SynchronizationDataSourceImpl(
            networkDataSource = get(BEAN_CACHED_NETWORK_DATASOURCE),
            databaseDataSource = get(BEAN_CACHED_DATABASE_DATASOURCE),

            credentialsSynchronizer = get(),

            publisherSynchronizer = get(BEAN_SYNCHRONIZER_PUBLISHER),
            assetSynchronizer = get(BEAN_SYNCHRONIZER_ASSET),
            downloadSynchronizer = get(BEAN_SYNCHRONIZER_DOWNLOAD),
            payoutSynchronizer = get(BEAN_SYNCHRONIZER_PAYOUT),
            periodSynchronizer = get(BEAN_SYNCHRONIZER_PERIOD),
            revenueSynchronizer = get(BEAN_SYNCHRONIZER_REVENUE),
            reviewSynchronizer = get(BEAN_SYNCHRONIZER_REVIEW),
            saleSynchronizer = get(BEAN_SYNCHRONIZER_SALE),
            userSynchronizer = get(BEAN_SYNCHRONIZER_USER)
        )
    }

    single {
        PublisherCredentialsSynchronizer(
            networkDataSource = get(BEAN_CACHED_NETWORK_DATASOURCE),
            credentials = get()
        )
    }
}

private val synchronizerModule = module {
    single(BEAN_SYNCHRONIZER_ASSET) {
        AssetSynchronizer(
            networkDataSource = get(BEAN_CACHED_NETWORK_DATASOURCE),
            databaseDataSource = get(BEAN_CACHED_DATABASE_DATASOURCE),
            mapper = get(BEAN_MAPPER_ASSET),
            filter = get(BEAN_FILTER_ASSET)
        )
    }

    single(BEAN_SYNCHRONIZER_PUBLISHER) {
        PublisherSynchronizer(
            networkDataSource = get(BEAN_CACHED_NETWORK_DATASOURCE),
            databaseDataSource = get(BEAN_CACHED_DATABASE_DATASOURCE),
            mapper = get(BEAN_MAPPER_PUBLISHER)
        )
    }

    single(BEAN_SYNCHRONIZER_SALE) {
        SaleSynchronizer(
            networkDataSource = get(BEAN_CACHED_NETWORK_DATASOURCE),
            databaseDataSource = get(BEAN_CACHED_DATABASE_DATASOURCE),
            periodsProvider = get(),
            mapper = get(BEAN_MAPPER_SALE),
            filter = get(BEAN_FILTER_SALE)
        )
    }

    single(BEAN_SYNCHRONIZER_REVENUE) {
        RevenueSynchronizer(
            networkDataSource = get(BEAN_CACHED_NETWORK_DATASOURCE),
            databaseDataSource = get(BEAN_CACHED_DATABASE_DATASOURCE),
            mapper = get(BEAN_MAPPER_REVENUE),
            filterByInstance = get(BEAN_FILTER_REVENUE_INSTANCE),
            filterByDate = get(BEAN_FILTER_REVENUE_DATE)
        )
    }

    single(BEAN_SYNCHRONIZER_PAYOUT) {
        PayoutSynchronizer(
            networkDataSource = get(BEAN_CACHED_NETWORK_DATASOURCE),
            databaseDataSource = get(BEAN_CACHED_DATABASE_DATASOURCE),
            mapper = get(BEAN_MAPPER_PAYOUT),
            filterByInstance = get(BEAN_FILTER_PAYOUT_INSTANCE),
            filterByDate = get(BEAN_FILTER_REVENUE_DATE)
        )
    }

    single(BEAN_SYNCHRONIZER_PERIOD) {
        PeriodSynchronizer(
            networkDataSource = get(BEAN_CACHED_NETWORK_DATASOURCE),
            databaseDataSource = get(BEAN_CACHED_DATABASE_DATASOURCE),
            filter = get(BEAN_FILTER_PERIOD)
        )
    }

    single(BEAN_SYNCHRONIZER_USER) {
        UserSynchronizer(
            networkDataSource = get(BEAN_CACHED_NETWORK_DATASOURCE),
            databaseDataSource = get(BEAN_CACHED_DATABASE_DATASOURCE),
            mapper = get(BEAN_MAPPER_USER)
        )
    }

    single(BEAN_SYNCHRONIZER_REVIEW) {
        ReviewSynchronizer(
            networkDataSource = get(BEAN_CACHED_NETWORK_DATASOURCE),
            databaseDataSource = get(BEAN_CACHED_DATABASE_DATASOURCE),
            mapper = get(BEAN_MAPPER_REVIEW),
            filter = get(BEAN_FILTER_REVIEW)
        )
    }
}

private val mapperModule = module {
    single(BEAN_MAPPER_REVIEW) {
        ReviewMapper(get(BEAN_PROVIDER_ASSET_BY_URL), get(BEAN_PROVIDER_USER_BY_NAME))
    }
    single(BEAN_MAPPER_ASSET) { AssetMapper.toListMapper() }
    single(BEAN_MAPPER_USER) { UserMapper.toListMapper() }
    single(BEAN_MAPPER_SALE) { SaleMapper(get(BEAN_PROVIDER_ASSET_BY_NAME)).toListMapper() }
    single(BEAN_MAPPER_REVENUE) { RevenueMapper(get(BEAN_PROVIDER_PERIOD_ID)).toListMapper() }
    single(BEAN_MAPPER_PAYOUT) { PayoutMapper(get(BEAN_PROVIDER_PERIOD_ID)).toListMapper() }
    single<_PublisherMapper>(BEAN_MAPPER_PUBLISHER) { PublisherMapper }
}

private val filterModule = module {
    single<_PeriodFilter>(BEAN_FILTER_PERIOD) { PeriodFilter(get(BEAN_PROVIDER_PERIOD_LAST)) }
    single<_AssetFilter>(BEAN_FILTER_ASSET) { AssetFilter(get(BEAN_PROVIDER_ASSET_BY_ID)) }
    single<_SaleFilter>(BEAN_FILTER_SALE) { SaleFilter(get(), get(BEAN_PROVIDER_SALE_DATE_LAST)) }
    single<_ReviewFilter>(BEAN_FILTER_REVIEW) { ReviewFilter(get(BEAN_PROVIDER_REVIEW)) }
    single<_RevenueFilter>(BEAN_FILTER_REVENUE_DATE) {
        RevenueDateFilter(
            get(
                BEAN_PROVIDER_CREDIT_DATE_LAST
            )
        )
    }
    single<_RevenueFilter>(BEAN_FILTER_REVENUE_INSTANCE) { RevenueInstanceFilter }
    single<_RevenueFilter>(BEAN_FILTER_PAYOUT_INSTANCE) { PayoutInstanceFilter }
}

private val providerModule = module {
    single<_AssetProviderById>(BEAN_PROVIDER_ASSET_BY_ID) {
        val datasource: DatabaseDataSource = get(BEAN_CACHED_DATABASE_DATASOURCE)
        return@single { id: Long -> datasource.getAsset(id).blockingSingle() }
    }

    single<_AssetProviderByName>(BEAN_PROVIDER_ASSET_BY_NAME) {
        val datasource: DatabaseDataSource = get(BEAN_CACHED_DATABASE_DATASOURCE)
        return@single { name: String -> datasource.getAsset(name).blockingSingle() }
    }
}