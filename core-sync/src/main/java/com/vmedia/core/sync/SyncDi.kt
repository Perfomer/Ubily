package com.vmedia.core.sync

import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.Filter
import com.vmedia.core.common.util.ListMapper
import com.vmedia.core.common.util.Mapper
import com.vmedia.core.common.util.toListMapper
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Publisher
import com.vmedia.core.data.internal.database.entity.Sale
import com.vmedia.core.network.entity.AssetDetailsDto
import com.vmedia.core.network.entity.AssetDto
import com.vmedia.core.network.entity.PublisherDto
import com.vmedia.core.network.entity.SaleDto
import com.vmedia.core.sync.cache.CachedDatabaseDataSourceDecorator
import com.vmedia.core.sync.cache.CachedNetworkDataSourceDecorator
import com.vmedia.core.sync.synchronizer.PublisherCredentialsSynchronizer
import com.vmedia.core.sync.synchronizer.SynchronizationPeriodsProvider
import com.vmedia.core.sync.synchronizer.Synchronizer
import com.vmedia.core.sync.synchronizer.asset.AssetFilter
import com.vmedia.core.sync.synchronizer.asset.AssetMapper
import com.vmedia.core.sync.synchronizer.asset.AssetModel
import com.vmedia.core.sync.synchronizer.asset.AssetSynchronizer
import com.vmedia.core.sync.synchronizer.publisher.PublisherMapper
import com.vmedia.core.sync.synchronizer.publisher.PublisherSynchronizer
import com.vmedia.core.sync.synchronizer.sale.SaleFilter
import com.vmedia.core.sync.synchronizer.sale.SaleMapper
import com.vmedia.core.sync.synchronizer.sale.SaleSynchronizer
import org.koin.dsl.module.module
import java.math.BigDecimal
import java.util.*

internal typealias _AssetProviderId = (id: Long) -> Asset
internal typealias _AssetProviderName = (name: String) -> Asset
internal typealias _PeriodsProvider = () -> List<Period>
internal typealias _LastSaleDateProvider = (period: Period, assetId: Long, priceUsd: BigDecimal) -> Date

internal typealias _AssetMapper = ListMapper<Pair<AssetDto, AssetDetailsDto>, AssetModel>
internal typealias _SaleMapper = ListMapper<SaleDto, Sale>
internal typealias _PublisherMapper = Mapper<Pair<Long, PublisherDto>, Publisher>

internal typealias _AssetFilter = Filter<AssetDto>
internal typealias _SaleFilter = Filter<Sale>

internal typealias _AssetSynchronizer = Synchronizer<SynchronizationEvent.AssetsReceived>
internal typealias _PublisherSynchronizer = Synchronizer<SynchronizationEvent.PublisherReceived>
internal typealias _ReviewSynchronizer = Synchronizer<SynchronizationEvent.CommentsReceived>
internal typealias _RevenueSynchronizer = Synchronizer<SynchronizationEvent.RevenuesReceived>
internal typealias _PayoutSynchronizer = Synchronizer<SynchronizationEvent.PayoutsReceived>
internal typealias _SaleSynchronizer = Synchronizer<SynchronizationEvent.SalesReceived>
internal typealias _DownloadSynchronizer = Synchronizer<SynchronizationEvent.FreeDownloadsReceived>
internal typealias _PeriodSynchronizer = Synchronizer<SynchronizationEvent.PeriodsReceived>

val syncModule = module {
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
            saleSynchronizer = get(BEAN_SYNCHRONIZER_SALE)
        )
    }

    single {
        PublisherCredentialsSynchronizer(
            networkDataSource = get(BEAN_CACHED_NETWORK_DATASOURCE),
            credentials = get()
        )
    }

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


    single(BEAN_MAPPER_ASSET) { AssetMapper.toListMapper() }
    single(BEAN_MAPPER_SALE) { SaleMapper(get(BEAN_PROVIDER_ASSET_NAME)).toListMapper() }
    single<_PublisherMapper>(BEAN_MAPPER_PUBLISHER) { PublisherMapper }

    single<_AssetFilter>(BEAN_FILTER_ASSET) { AssetFilter(get(BEAN_PROVIDER_ASSET_ID)) }
    single<_SaleFilter>(BEAN_FILTER_SALE) { SaleFilter(get(), get(BEAN_PROVIDER_SALE_DATE_LAST)) }

    single<_AssetProviderId>(BEAN_PROVIDER_ASSET_ID) {
        val datasource: DatabaseDataSource = get(BEAN_CACHED_DATABASE_DATASOURCE)
        return@single { id: Long -> datasource.getAsset(id).blockingSingle() }
    }

    single<_AssetProviderName>(BEAN_PROVIDER_ASSET_ID) {
        val datasource: DatabaseDataSource = get(BEAN_CACHED_DATABASE_DATASOURCE)
        return@single { name: String -> datasource.getAsset(name).blockingSingle() }
    }
}

private const val BEAN_CACHED_DATABASE_DATASOURCE = "CachedDatabaseDataSource"
private const val BEAN_CACHED_NETWORK_DATASOURCE = "CachedNetworkDataSource"

private const val BEAN_MAPPER_ASSET = "AssetMapper"
private const val BEAN_MAPPER_SALE = "SaleMapper"
private const val BEAN_MAPPER_PUBLISHER = "PublisherMapper"

private const val BEAN_FILTER_ASSET = "AssetFilter"
private const val BEAN_FILTER_SALE = "SaleFilter"

private const val BEAN_PROVIDER_ASSET_ID = "AssetProviderId"
private const val BEAN_PROVIDER_ASSET_NAME = "AssetProviderName"
private const val BEAN_PROVIDER_SALE_DATE_LAST = "LastSaleDateProvider"

private const val BEAN_SYNCHRONIZER_ASSET = "AssetSynchronizer"
private const val BEAN_SYNCHRONIZER_PUBLISHER = "PublisherSynchronizer"
private const val BEAN_SYNCHRONIZER_REVIEW = "ReviewSynchronizer"
private const val BEAN_SYNCHRONIZER_REVENUE = "RevenueSynchronizer"
private const val BEAN_SYNCHRONIZER_PAYOUT = "PayoutSynchronizer"
private const val BEAN_SYNCHRONIZER_SALE = "SaleSynchronizer"
private const val BEAN_SYNCHRONIZER_DOWNLOAD = "FreeDownloadSynchronizer"
private const val BEAN_SYNCHRONIZER_PERIOD = "PeriodSynchronizer"