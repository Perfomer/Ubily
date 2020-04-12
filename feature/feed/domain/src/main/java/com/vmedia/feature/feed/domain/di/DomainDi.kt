package com.vmedia.feature.feed.domain.di

import com.vmedia.core.common.util.ObservableListMapper
import com.vmedia.core.common.util.ObservableMapper
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.obj.EventInfo
import com.vmedia.feature.feed.domain.FeedInteractor
import com.vmedia.feature.feed.domain.mapper.*
import org.koin.dsl.module

internal typealias _EventMapper = ObservableListMapper<Event, EventInfo<*>>
internal typealias _SaleMapper = _EventInfoMapper<EventInfo.EventListInfo.EventSale>
internal typealias _DownloadMapper = _EventInfoMapper<EventInfo.EventListInfo.EventFreeDownload>
internal typealias _AssetMapper = _EventInfoMapper<EventInfo.EventListInfo.EventAsset>
internal typealias _ReviewMapper = _EventInfoMapper<EventInfo.EventReview>
internal typealias _RevenueMapper = _EventInfoMapper<EventInfo.EventRevenue>
internal typealias _PayoutMapper = _EventInfoMapper<EventInfo.EventPayout>

private typealias _EventInfoMapper<T> = ObservableMapper<Event, T>

val domainModule = module {
    single { FeedInteractor(get(), get<EventMapper>()) }

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